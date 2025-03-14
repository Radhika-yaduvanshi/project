package com.blogwebsite.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwebsite.admin.proxy.AdminProxy;
import com.blogwebsite.admin.proxy.UserProxy;
import com.blogwebsite.admin.service.AdminService;


@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService service;

	@PostMapping("/registerAdmin") //working
	public ResponseEntity<?> registerAdmin(@RequestBody AdminProxy adminProxy)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.registerAdmin(adminProxy));
	}
	//------pending
	
	
	@PostMapping("/registerUser") //user & admin 
	public ResponseEntity<?> registerUser(@RequestBody UserProxy user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(user));
	}
	
	
	
	
	//working -delete user by id
//	@DeleteMapping("/deleteById/{id}") //admin
//	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
//	{
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.deleteUser(id));
//	}
	
	
	//working - get all users
//	@GetMapping("/getAll") //admin 
//	public ResponseEntity<?> getAllUser()
//	{
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.get);
//	}
	
	//working -get user by usernamme
	@GetMapping("/getByUserName/{uname}") //admin
	public ResponseEntity<?> getUserByUserName(@PathVariable("uname") String userName)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getUserByUserName(userName));
	}
	
	@GetMapping("/searchBlogByTitle/{title}") //working -user
	public ResponseEntity<?> searchBlogByTitle(@PathVariable("title") String title)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.searchByBlogTitle(title));
	}
	
	//get all blogs
	@GetMapping("/getAllBlogs") //working -user
	public ResponseEntity<?> getAllBlog()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.getAllBlogs());
	}
	
	//delete blog
	@DeleteMapping("/delete/{id}") //working -user
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.deleteBlog(id));
	}
	
	@GetMapping("/getAllUsers") //admin 
	public ResponseEntity<?> getAllUser(){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.findAllUsers());

	}
	
	@DeleteMapping("/deleteById/{id}") 
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.deleteUser(id));
	}
	
	@GetMapping("/getAdminBYName/{name}")
	public ResponseEntity<?> findAdminByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.findAdminByName(name));
	}
	
}
