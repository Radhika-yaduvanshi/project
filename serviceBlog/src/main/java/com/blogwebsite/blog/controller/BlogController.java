package com.blogwebsite.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.service.impl.BlogServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private BlogServiceImpl blogImpl;


	@PostMapping("/create")
	public String createBlogWithImages(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("userId") Integer userId,
			@RequestParam("categoryId") Integer categoryId,
			@RequestParam("images") List<MultipartFile> images) {

		try {
			blogImpl.createBlogWithImages(title, content, userId, categoryId, images);
			return "Blog created successfully with images.";
		} catch (IOException e) {
			return "Failed to upload images: " + e.getMessage();
		}
	}
	
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
	public ResponseEntity<?> getAllBlog()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getAllBlogs());
	}
	
	
	//get user by id for user id reference 
	@GetMapping("/getUserById/{id}") //working - to store user id in blog table
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getUserByUserId(id));
	}
	
	//add comment on blog - working
	@PostMapping("/AddComment/{id}")
	public ResponseEntity<?> addComment(@PathVariable("id") Integer id,@RequestBody CommentProxy commentProxy)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.addCommentToBlog(id, commentProxy));
	}
	
	
	//get only comment by blog id - working
	@GetMapping("/getCommentsByBlogId/{id}")
	public ResponseEntity<?> getCommentsByBlogId(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getCommentsByBlogId(id));
	}
	

	//get blog by id - working
	@GetMapping("/getBlogById/{id}") //show all comments on blog by id
	public ResponseEntity<?> getBlogById1(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getBlogById(id));
	}
	
	@PostMapping("/searchBlogByTitleAndCategory") //working [added- 22-03-2025]
	public ResponseEntity<?> searchByBlogTitleAndCategoryName(@RequestBody BlogProxy blogProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(blogImpl.searchBlogByTitleAndCategory(blogProxy));
	}
}
