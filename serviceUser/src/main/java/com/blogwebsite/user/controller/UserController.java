package com.blogwebsite.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.service.impl.UserServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userImpl;


	@PostMapping("/images")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			// Save the file to the directory
			String filePath = userImpl.saveImage(file);
			return ResponseEntity.ok("Image uploaded successfully: " + filePath);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
		}
	}

	@GetMapping("/images/{filename}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		try {
			return  userImpl.getImage(filename);
		} catch (MalformedURLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


	@PostMapping("/register-with-image")
	public ResponseEntity<String> registerUser(
			@RequestParam("user") String userJson,
			@RequestPart("file") MultipartFile file) throws IOException {

		// Convert JSON string to UserProxy object
		ObjectMapper objectMapper = new ObjectMapper();
		UserProxy user = objectMapper.readValue(userJson, UserProxy.class);

		String response = userImpl.registerUserwithImage(user, file);
		return ResponseEntity.ok(response);
	}


	//working register user
	@PostMapping("/register") //user & admin 
	public ResponseEntity<?> registerUser(@RequestBody UserProxy user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.registerUser(user));
	}
	
	//working -delete user by id
	@DeleteMapping("/deleteById/{id}") //admin
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.deleteUser(id));
	}
	
	
	//working - get all users
	@GetMapping("/getAll") //admin 
	public ResponseEntity<?> getAllUser()
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.getAllUser());
	}
	
	//working -get user by usernamme
	@GetMapping("/getByUserName/{uname}") //admin
	public ResponseEntity<?> getUserByUserName(@PathVariable("uname") String userName)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.getUserByUserName(userName));
	}
	
	//update user by user id
	@PutMapping("/update/{id}") //working
	public ResponseEntity<?> updateUserById(@RequestBody UserProxy user,@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.updateUserById(id, user));
	}
	
	//get user by user id
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userImpl.getUserByUserId(id));
	}
	
	//search blog by title
	@GetMapping("/getBlogTitle/{title}") //working
	public ResponseEntity<?> searchBlogByTitle(@PathVariable("title") String title)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userImpl.searchBlogByTitle(title));
	}
	
	//user can create blog
	@PostMapping("/createBlog/{id}") //working
	public ResponseEntity<?> createBlog(@RequestBody BlogProxy blogProxy,@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.createBlog(blogProxy, id));
	}

	//user can delete blog
	@DeleteMapping("/deleteBlog/{id}") //working
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.deleteBlog(id));
	}
	
	//user can existing update blog
	@PutMapping("/updateBlog/{id}") //working
	public ResponseEntity<?> updateBlog(@RequestBody BlogProxy blogProxy,@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.updateBlog(blogProxy, id));
	}
	
	//get all blog
	@GetMapping("/getAllBlog") //working
	public ResponseEntity<?> getAllBlogs()
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.getAllBlogs());
	}
	
	//filter blog by category 
	@GetMapping("/searchBlogByCategory/{category}") //working
	public ResponseEntity<?> searchBlogByCategory(@PathVariable("category") String category)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.searchBlogByCategory(category));
	}
	
	@PostMapping("/AddComment/{id}")
	public ResponseEntity<?> addComment(@PathVariable("id") Integer id,@RequestBody CommentProxy commentProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.addComment(id, commentProxy));
	}
	@PostMapping("/searchBlogByTitleAndCategory") //working [added- 22-03-2025]
	public ResponseEntity<?> searchByBlogTitleAndCategoryName(@RequestBody BlogProxy blogProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userImpl.searchByBlogTitleAndCategoryName(blogProxy));
	}
 }
