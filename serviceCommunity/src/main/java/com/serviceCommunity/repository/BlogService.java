package com.serviceCommunity.repository;



import com.serviceCommunity.proxy.BlogProxy;
import com.serviceCommunity.proxy.UserProxy;

import java.util.List;

public interface BlogService {
	//crud on blog
	
	//create blog
	public String createBlog(BlogProxy blogproxy, Integer id); //user
	
	//delete blog
	public String deleteBlog(Integer id);
	
	//update blog
	public String updateBlog(BlogProxy blogProxy,Integer id);

	public List<BlogProxy> searchByBlogTitle(String title);
	
	//get all blogs
	public List<BlogProxy> getAllBlogs();
	
	public UserProxy getUserByUserId(Integer id);
	
//	public List<CommentProxy> getCommentsByBlogId(Integer id);
	
	public BlogProxy getBlogById(Integer id);

//	String addCommentToBlog(Integer blogId, CommentProxy commentProxy);
	
	public List<BlogProxy> searchBlogByTitleAndCategory(BlogProxy blogProxy);
	public String createBlog(BlogProxy blogproxy);


	public List<BlogProxy> getBlogByUserId(Integer id);
}
