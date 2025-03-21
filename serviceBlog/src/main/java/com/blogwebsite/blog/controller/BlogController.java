package com.blogwebsite.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.service.impl.BlogServiceImpl;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private BlogServiceImpl blogImpl;
	
	//create blog
	@PostMapping("/save/{id}") //working -user
	public ResponseEntity<?> saveBlog(@RequestBody BlogProxy blogProxy,@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.createBlog(blogProxy,id));
	}
	
	//delete blog
	@DeleteMapping("/delete/{id}") //working -user
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.deleteBlog(id));
	}
	
	//update blog
	@PutMapping("/update/{id}")  //working -user
	public ResponseEntity<?> updateBlog(@RequestBody BlogProxy blogProxy,@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.updateBlog(blogProxy, id));
	}
	
	//search blog by title 
	@GetMapping("/searchBlogByTitle/{title}") //working -user
	public ResponseEntity<?> searchBlogByTitle(@PathVariable("title") String title)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.searchByBlogTitle(title));
	}
	
	//get all blogs
	@GetMapping("/getAllBlogs") //working -user
	public ResponseEntity<?> getAllBlog(
			@RequestParam(value = "pageNumber",defaultValue ="1",required = false ) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue ="3",required = false ) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue ="id",required = false ) String sortBy
			)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getAllBlogs( pageNumber,pageSize,sortBy));
	}
	
	
	//get user by id for user id reference 
	@GetMapping("/getUserById/{id}") //working - to store user id in blog table
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getUserByUserId(id));
	}
}
