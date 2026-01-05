package com.demo.web.controller.admin;

import com.demo.exception.AppException;
import com.demo.security.SessionKeys;
import com.demo.service.CategoryService;
import com.demo.service.ProductService;
import com.demo.service.impl.ProductQueryService;
import com.demo.web.dto.ProductForm;
import com.demo.web.filter.ProductFilter;
import com.demo.web.paging.PageRequest;
import com.demo.web.util.Redirects;
import com.demo.web.util.Urls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductQueryService productQueryService;

    public AdminProductController(
    		ProductService productService,
    		CategoryService categoryService,
          	ProductQueryService productQueryService) 
    {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productQueryService = productQueryService;
    }

    // ===== LIST =====
    @GetMapping
    public String list(
    		@RequestParam(value = "categoryId", required = false) Integer categoryId,
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value="sort", required=false) String sort,
    	    @RequestParam(value="dir", required=false) String dir,
               	Model model,
               	HttpServletRequest req) {
    	// lưu last list url (enterprise pattern)
        req.getSession().setAttribute(SessionKeys.ADMIN_PRODUCTS_LIST_URL, Urls.fullPathWithQuery(req));
        
        var allowedSort = java.util.Set.of("id", "name", "price", "stock");

        var pr = PageRequest.of(page, size, sort, dir, allowedSort);

        var filter = new ProductFilter(categoryId, keyword);

        var result = productQueryService.search(filter, pr);

        model.addAttribute("categories", categoryService.list());
        
        model.addAttribute("filter", filter);
        model.addAttribute("page", result);
        model.addAttribute("sort", pr.sort());
        model.addAttribute("dir", pr.dir());
        
        return "admin/product/list";
    }

    // ===== CREATE: form =====
    @GetMapping("/new")
    public String createForm(Model model, HttpServletRequest req) {
        model.addAttribute("form", new ProductForm());
        model.addAttribute("backUrl", Urls.backUrl(req, SessionKeys.ADMIN_PRODUCTS_LIST_URL, "/admin/products"));
        fillRefs(model);
        return "admin/product/form";
    }

    // ===== CREATE: submit =====
    @PostMapping
    public String create(@Valid @ModelAttribute("form") ProductForm form,
                         BindingResult br,
                         Model model,
                         HttpServletRequest req,
                         RedirectAttributes ra) {
        if (br.hasErrors()) {
            fillRefs(model);
            return "admin/product/form";
        }

        try {
            productService.create(form);
            ra.addFlashAttribute("success", "Created product successfully");
            return Redirects.back(req, SessionKeys.ADMIN_PRODUCTS_LIST_URL, "/admin/products");
        } catch (AppException ex) {
            // lỗi nghiệp vụ: unique name, category not found, etc.
            br.reject("business", ex.getMessage());
            model.addAttribute("mode", "create");
            fillRefs(model);
            return "admin/product/form";
        }
    }

    // ===== EDIT: form =====
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model, HttpServletRequest req) {
        var p = productService.getRequired(id);

        ProductForm form = new ProductForm();
        form.setId(p.getId());
        form.setName(p.getName());
        form.setStock(p.getStock());
        form.setPrice(p.getPrice());
        form.setCategoryId(p.getCategory().getId());

        model.addAttribute("form", form);
        model.addAttribute("backUrl", Urls.backUrl(req, SessionKeys.ADMIN_PRODUCTS_LIST_URL, "/admin/products"));
        fillRefs(model);
        return "admin/product/form";
    }

    // ===== UPDATE: submit =====
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("form") ProductForm form,
                         BindingResult br,
                         Model model,
                         HttpServletRequest req,
                         RedirectAttributes ra) {

        // ép form.id theo path để khỏi bị client sửa bậy
        form.setId(id);

        if (br.hasErrors()) {
            fillRefs(model);
            return "admin/product/form";
        }

        try {
            productService.update(id, form); // ✅ service nhận DTO
            ra.addFlashAttribute("success", "Updated product successfully");

            return Redirects.back(req, SessionKeys.ADMIN_PRODUCTS_LIST_URL, "/admin/products");
        
        } catch (AppException ex) {
            br.reject("business", ex.getMessage());
            fillRefs(model);
            return "admin/product/form";
        }
    }

    // ===== DELETE (soft) =====
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id,
    		HttpServletRequest req, RedirectAttributes ra) {
      
            productService.softDelete(id);
            ra.addFlashAttribute("success", "Deleted product successfully");
            
            return Redirects.back(req, SessionKeys.ADMIN_PRODUCTS_LIST_URL, "/admin/products");
    }

    // ===== helpers =====
    private void fillRefs(Model model) {
        model.addAttribute("categories", categoryService.list());
    }

    
}
