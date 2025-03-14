package com.blogwebsite.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blogwebsite.admin.domain.AdminEntity;
import com.blogwebsite.admin.feingClient.BlogClient;
import com.blogwebsite.admin.feingClient.UserClient;
import com.blogwebsite.admin.proxy.AdminProxy;
import com.blogwebsite.admin.proxy.BlogProxy;
import com.blogwebsite.admin.proxy.UserProxy;
import com.blogwebsite.admin.repository.AdminRepo;
import com.blogwebsite.admin.service.AdminService;
import com.blogwebsite.admin.utils.Helper;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private Helper helper;
	@Autowired
	private BlogClient blogClient;
	@Autowired
	private UserClient userClient;
	
	@Override
	public String registerAdmin(AdminProxy admin) {
		 adminRepo.save(helper.convert(admin, AdminEntity.class));
		return "register successfully...";
	}

	@Override
	public List<UserProxy> findAllUsers() { 
		List<UserProxy> allUser = userClient.getAllUser();
		return allUser;
	}

	@Override
	public UserProxy getUserByUserName(String userName) {
		return  userClient.getUserByUserName(userName);
	}

	@Override
	public String deleteUser(Integer id) {
		return	 userClient.deleteById(id);
		 
	}

	@Override
	public String registerUser(UserProxy user) {
		userClient.registerUser(user);
		return "User Registered Successfully";
	}

	@Override
	public List<BlogProxy> getAllBlogs() {
		return blogClient.getAllBlogs();
		 
	}

	@Override
	public List<BlogProxy> searchByBlogTitle(String title) {
		return blogClient.searchBlogByTitle(title);
	}

	@Override
	public String deleteBlog(Integer id) {
		return blogClient.deleteBlog(id);
	}

	@Override
	public List<AdminEntity> findAdminByName(String name) {
		return adminRepo.findByName(name);
	}

}
