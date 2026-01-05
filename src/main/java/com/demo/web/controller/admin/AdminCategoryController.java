package com.demo.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.list());
        return "admin/category/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("name", "");
        return "admin/category/form";
    }

    // CREATE (PRG)
    @PostMapping
    public String create(@RequestParam("name") String name, RedirectAttributes ra) {
        categoryService.create(name);
        ra.addFlashAttribute("success", "Created category successfully");
        return "redirect:/admin/categories";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
    	var c = categoryService.getRequired(id);
    	model.addAttribute("mode", "edit");
        model.addAttribute("id", c.getId());
        model.addAttribute("name", c.getName());
        return "admin/category/form";
    }

    // UPDATE (PRG)
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Integer id, 
    		@RequestParam("name") String name,
    		RedirectAttributes ra) {
        categoryService.update(id, name);
        ra.addFlashAttribute("success", "Updated category successfully");
        return "redirect:/admin/categories";
    }

 // DELETE (PRG) - dùng POST để tránh xóa bằng link GET
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        categoryService.delete(id);
        ra.addFlashAttribute("success", "Deleted category successfully");
        return "redirect:/admin/categories";
    }
}
