package com.blogwebsite.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	//delete category
	@DeleteMapping("/delete/{id}") //working -user
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.deleteCategory(id));
	}

	//update blog
	@PutMapping("/update/{id}")  //working -user
	public ResponseEntity<?> updateBlog(@RequestBody CategoryProxy categoryProxy,@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateCategoryProxy(categoryProxy, id));
	}
}
