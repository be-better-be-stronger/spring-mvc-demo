package com.demo.web.controller;

import com.demo.security.SessionKeys;
import com.demo.web.dto.ProductListRow;
import com.demo.web.filter.ProductFilter;
import com.demo.web.paging.PageRequest;
import com.demo.web.paging.PageResponse;
import com.demo.web.util.Urls;
import com.demo.entity.Category;

// tuỳ mày đang đặt service/dto ở package nào thì sửa import cho đúng:
import com.demo.service.ProductService;
import com.demo.service.impl.ProductQueryService;
import com.demo.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {

	private static final Set<String> ALLOWED_SORT = Set.of("name", "price");

	private final ProductQueryService productQueryService;
	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductController(ProductQueryService productQueryService, ProductService productService,
			CategoryService categoryService) {
		this.productQueryService = productQueryService;
		this.productService = productService;
		this.categoryService = categoryService;
	}

	/**
	 * Bơm categories cho dropdown filter (list page). NOTE: gọi mọi request trong
	 * controller này đều có "categories" trong model.
	 */
	@ModelAttribute("categories")
	public List<Category> categories() {
		return categoryService.list();
	}

	/**
	 * List sản phẩm cho customer: - chỉ hiển thị active=true - có filter theo
	 * keyword/category (tuỳ ProductFilter mày đang support) - có pagination - lưu
	 * URL hiện tại vào session để Back từ detail chuẩn
	 */
	@GetMapping
	public String list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "dir", required = false) String dir,
			@RequestParam(name = "keyword", required = false) String keyword, 
			HttpServletRequest req, 
			Model model) {

		// lưu URL list để detail/back dùng
		HttpSession session = req.getSession(true);
		session.setAttribute(SessionKeys.USER_PRODUCTS_LIST_URL, Urls.fullPathWithQuery(req));

		ProductFilter filter = new ProductFilter(categoryId, keyword);

		PageRequest pr = PageRequest.of(page, size, sort, dir, ALLOWED_SORT);
		PageResponse<ProductListRow> result = productQueryService.search(filter, pr);

		model.addAttribute("filter", filter);
		model.addAttribute("page", result);
		model.addAttribute("pr", pr);

		return "shop/product/list"; // /WEB-INF/views/shop/product/list.jsp
	}

	/**
	 * Detail sản phẩm: - nếu không tồn tại hoặc inactive -> 404 - show ra view +
	 * backUrl (lấy từ session list url)
	 */
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") int id, HttpServletRequest req, Model model) {
		var p = productService.findPublicDetailById(id);
		
		String back = (String) req.getSession(true)
	            .getAttribute(SessionKeys.USER_PRODUCTS_LIST_URL);
	    if (back == null || back.isBlank()) back = "/products";

	    model.addAttribute("p", p);
	    model.addAttribute("back", back);

	    return "shop/product/detail";
	}


}
