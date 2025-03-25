package com.blogwebsite.blog.service.impl;

import java.util.List;
import java.util.Optional;


import com.blogwebsite.blog.paging.BlogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public BlogResponse getAllBlogs(Integer pageNumber, Integer pageSize,String sortBy) {

		Pageable p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
		Page<BlogEntity> pageBlog=this.blogRepo.findAll(p);
		List<BlogEntity>allblogs=pageBlog.getContent();
		List<BlogProxy> blogProxy = helper.convertBlogListEntityToProxy(allblogs);
		BlogResponse blogResponse=new BlogResponse();
		blogResponse.setContent(blogProxy);
		blogResponse.setPageNumber((long) pageBlog.getNumber());
		blogResponse.setPageSize((long) pageBlog.getSize());
		blogResponse.setTotalElements(pageBlog.getTotalElements());
		blogResponse.setTotalPages((long) pageBlog.getTotalPages());
		blogResponse.setLastPage(pageBlog.isLast());
//		List<BlogEntity> all = blogRepo.findAll();
		return blogResponse;
	}
	
	public UserProxy getUserByUserId(Integer id)
	{
	return	userClient.getUserByUserId(id);
	}
}
