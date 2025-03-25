package com.blogwebsite.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwebsite.user.FeignClient.BlogClient;
import com.blogwebsite.user.FeignClient.CategoryClient;
import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.repository.UserRepo;
import com.blogwebsite.user.service.UserService;
import com.blogwebsite.user.utils.Helper;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Helper helper;
	
	@Autowired
	private CategoryClient categoryClient;
	
	@Autowired
	private BlogClient blogClient;
	
	//private final String blogUrl="http://localhost:8088/blog/";
	
	@Override
	public String registerUser(UserProxy user) {
		userRepo.save(helper.convert(user, UserEntity.class));
		return "register successfully";
	}

	@Override
	public String deleteUser(Integer id) {
			userRepo.deleteById(id);
		return "user deleted successfully";
	}

	@Override
	public String createBlog(BlogProxy blog,Integer id) {
		blogClient.createBlog(blog, id);
		return "saved";
	}

	@Override
	public String deleteBlog(Integer id) {
		blogClient.deleteBlog(id);
		return null;
	}

	@Override
	public String updateBlog(BlogProxy blog,Integer id) {
		blogClient.updateBlog(blog, id);
		return "updated...";
	}

	@Override
	public List<UserProxy> getAllUser() {
		return helper.convertList(userRepo.findAll(), UserProxy.class);
	}

	@Override
	public UserProxy getUserByUserName(String userName) {
		return helper.convert(userRepo.findByUserName(userName), UserProxy.class);
	}

	@Override //search blog by user
	public List<BlogProxy> searchBlogByTitle(String title) {
		
		return blogClient.searchBlogByTitle(title);
	}

	@Override
	public UserProxy updateUserById(Integer id, UserProxy user) {
		
		Optional<UserEntity> byId = userRepo.findById(id);
		
		if(byId.isPresent())
		{
			UserEntity userEntity = byId.get();
			userEntity.setUserName(user.getUserName());
			userEntity.setEmail(user.getEmail());
			userEntity.setPassword(user.getPassword());
			userRepo.save(userEntity);
		}
		return user;
	}

	@Override
	public UserProxy getUserByUserId(Integer id) //working
	{
		return helper.convert(userRepo.findById(id), UserProxy.class);
	}

	@Override
	public List<BlogProxy> getAllBlogs() //working
	{
		return 	blogClient.getAllBlogs();
	}

	@Override
	public List<BlogProxy> searchBlogByCategory(String category) //working
	{
		return categoryClient.searchBlogByCategory(category);
	}

	@Override
	public String addComment(Integer id, CommentProxy commentProxy) {
		blogClient.addComment(id, commentProxy);
		return "comment added successfully";
	}

	@Override
	public List<BlogProxy> searchByBlogTitleAndCategoryName(BlogProxy blogProxy) {
		return blogClient.searchByBlogTitleAndCategoryName(blogProxy);
	}
	
	
}
