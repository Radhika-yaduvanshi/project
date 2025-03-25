package com.blogwebsite.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CategoryProxy;
import com.blogwebsite.blog.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addCategory(@RequestBody CategoryProxy categoryProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(categoryProxy));
	}
	
	@GetMapping("/searchBlogByCategory/{category}")
	public List<BlogProxy> searchBlogByCategory(@PathVariable("category") String category)
	{
//		return ResponseEntity.status(HttpStatus.OK).body(categoryService.searchByCategory(category));
		return categoryService.searchByCategory(category);
	}
	

	@GetMapping("/getAllCategory")
	public ResponseEntity<?> getAllCategory()
	{
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategory());
	}
}
