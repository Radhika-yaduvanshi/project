package com.blogwebsite.admin.service;

import java.util.List;

import com.blogwebsite.admin.domain.AdminEntity;
import com.blogwebsite.admin.proxy.AdminProxy;
import com.blogwebsite.admin.proxy.BlogProxy;
import com.blogwebsite.admin.proxy.UserProxy;

public interface AdminService {

	//register admmin
	public String registerAdmin(AdminProxy admin);
	
	//find all user
	public List<UserProxy> findAllUsers();
	
	//find user by username
	public UserProxy getUserByUserName(String userName);
	
	//delete user
	public String deleteUser(Integer id);
	
	//add user
	public String registerUser(UserProxy user);
	
	//find all blog
	public List<BlogProxy> getAllBlogs();
	
	//find blog by title
	public List<BlogProxy> searchByBlogTitle(String title);
	
	//delete blog
	public String deleteBlog(Integer id);
	
	public List<AdminEntity> findAdminByName(String name);
	
}
