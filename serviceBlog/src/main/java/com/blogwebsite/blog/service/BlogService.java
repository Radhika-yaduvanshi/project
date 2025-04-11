package com.blogwebsite.blog.service;

import java.util.List;

import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.proxy.UserProxy;

public interface BlogService {
	//crud on blog
	
	//create blog
	public String createBlog(BlogProxy blogproxy,Integer id); //user
	
	//delete blog
	public String deleteBlog(Integer id);
	
	//update blog
	public String updateBlog(BlogProxy blogProxy,Integer id);

	public List<BlogProxy> searchByBlogTitle(String title);
	
	//get all blogs
	public List<BlogProxy> getAllBlogs();
	
	public UserProxy getUserByUserId(Integer id);
	
	public List<CommentProxy> getCommentsByBlogId(Integer id);
	
	public BlogProxy getBlogById(Integer id);

	String addCommentToBlog(Integer blogId, CommentProxy commentProxy);
	
	public List<BlogProxy> searchBlogByTitleAndCategory(BlogProxy blogProxy);
	public String createBlog(BlogProxy blogproxy);


	public List<BlogProxy> getBlogByUserId(Integer id);
}
