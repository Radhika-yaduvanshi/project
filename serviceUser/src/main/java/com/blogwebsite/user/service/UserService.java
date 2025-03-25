package com.blogwebsite.user.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;
import com.blogwebsite.user.proxy.UserProxy;
import org.springframework.web.multipart.MultipartFile;

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
	
	//filter blog by category
	public List<BlogProxy> searchBlogByCategory(@PathVariable("category") String category); //working
	
	public String addComment(@PathVariable("id") Integer id,@RequestBody CommentProxy commentProxy); //working
	
	public List<BlogProxy> searchByBlogTitleAndCategoryName(BlogProxy blogProxy);
//	public String uploadProfileImage(Integer id, MultipartFile image);
	public String registerUserWithProfile(String name, String email, String password, MultipartFile profilePhoto);
	public String uploadProfileImage(Integer id, MultipartFile image);

	byte[] uploadProfileImage(MultipartFile image) throws IOException;
}
