package com.blogwebsite.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userImpl;
	
	//working register user
	@PostMapping("/register") //user & admin 
	public ResponseEntity<?> registerUser(@RequestBody UserProxy user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(userImpl.registerUser(user));
	}
//
//	//working -delete user by id
//	@DeleteMapping("/deleteById/{id}") //admin
//	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
//	{
//		return ResponseEntity.status(HttpStatus.OK).body(userImpl.deleteUser(id));
//	}//was creating issue with fornt end api call cause it is ok 200 response


	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
		userImpl.deleteUser(id);
		return ResponseEntity.noContent().build(); // ✅ Returns 204 No Content
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
 }
