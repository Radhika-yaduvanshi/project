package com.blogwebsite.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.UserProxy;

public interface UserService {

	//register user	 
	public String registerUser(UserProxy user); //working
	
	//delete user
	public String deleteUser(Integer id); //working
	
	//add user
	
	//create blog
	public String createBlog(BlogProxy blog,Integer id); 

	//delete blog
	public String deleteBlog(Integer id);
	
	//update blog
	public String updateBlog(BlogProxy blog,Integer id);
	
	//find all user
	public List<UserProxy> getAllUser(); //working
	
	//find user by username
	public UserProxy getUserByUserName(String userName); 
	
	//search blog
	public List<BlogProxy> searchBlogByTitle(String title); //working
	
	public UserProxy updateUserById(Integer id,UserProxy user);
	
	public UserProxy getUserByUserId(Integer id); //working
	
	public List<BlogProxy> getAllBlogs(); //working
}
