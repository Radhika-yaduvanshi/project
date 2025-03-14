package com.blogwebsite.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blogwebsite.blog.FeignClient.UserClient;
import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.UserProxy;
import com.blogwebsite.blog.repository.BlogRepo;
import com.blogwebsite.blog.service.BlogService;
import com.blogwebsite.blog.utils.Helper;

@Service
public class BlogServiceImpl implements BlogService
{
	
	private static final String USER_SERVICE_URL = "http://localhost:8087/user/";
	
	@Autowired
	private BlogRepo blogRepo;

	@Autowired
	private Helper helper;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserClient userClient;
	
	@Override
	public String createBlog(BlogProxy blogproxy,Integer userid) {
		BlogEntity convertBlogProxyToEntity = helper.convertBlogProxyToEntity(blogproxy);
//		UserProxy user = restTemplate.getForObject(USER_SERVICE_URL+"/getById/"+userid, UserProxy.class);
		
		UserProxy user = userClient.getUserByUserId(userid);
		System.err.println(user);
		
		if(user!=null)
		{
			System.out.println(userid);
			convertBlogProxyToEntity.setUser_id(userid);
			blogRepo.save(convertBlogProxyToEntity);
		}
		return "saved successfully";
	}

	@Override
	public String deleteBlog(Integer id) {
		blogRepo.deleteById(id);
		return "deleted successfully";
	}

	@Override
	public String updateBlog(BlogProxy blogProxy, Integer id) {
		Optional<BlogEntity> byId = blogRepo.findById(id);
		if(byId.isPresent())
		{
			BlogEntity blogEntity = byId.get();
			blogEntity.setTitle(blogProxy.getTitle());
			blogEntity.setContent(blogProxy.getContent());
			blogRepo.save(blogEntity);
		}
		return "updated successfully";
	}

	//search blog by title
	@Override
	public List<BlogProxy> searchByBlogTitle(String title) {
		List<BlogEntity> byTitle = blogRepo.findByTitle(title);
		return helper.convertBlogListEntityToProxy(byTitle);
	}

	@Override
	public List<BlogProxy> getAllBlogs() {
		List<BlogEntity> all = blogRepo.findAll();
		return helper.convertBlogListEntityToProxy(all);
	}
	
	public UserProxy getUserByUserId(Integer id)
	{
	return	userClient.getUserByUserId(id);
	}
}
