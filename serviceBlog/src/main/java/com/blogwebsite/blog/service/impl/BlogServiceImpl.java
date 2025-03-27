package com.blogwebsite.blog.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.blogwebsite.blog.domain.BlogImage;
import com.blogwebsite.blog.repository.BlogImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwebsite.blog.FeignClient.UserClient;
import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.domain.Category;
import com.blogwebsite.blog.domain.Comment;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.proxy.UserProxy;
import com.blogwebsite.blog.repository.BlogRepo;
import com.blogwebsite.blog.repository.CategoryRepo;
import com.blogwebsite.blog.service.BlogService;
import com.blogwebsite.blog.utils.Helper;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BlogServiceImpl implements BlogService
{
	
	
	@Autowired
	private BlogRepo blogRepo;

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private Helper helper;

	
	@Autowired
	private UserClient userClient;

	@Autowired
	private BlogImageRepo blogImageRepo;



	@Override
	public String createBlog(BlogProxy blogproxy,Integer userid) {
		
		BlogEntity convertBlogProxyToEntity = helper.convert(blogproxy,BlogEntity.class);
		
		Optional<Category> categoryId = categoryRepo.findById(blogproxy.getCategory().getId());
		
		System.err.println(categoryId);
		UserProxy user = userClient.getUserByUserId(userid);
		if (categoryId.isPresent()) {
			convertBlogProxyToEntity.setCategory(categoryId.get());
		} else {
			// Log or throw an exception if category is not found
			System.err.println("Category not found for ID: " + blogproxy.getCategory().getId());
			return "Category not found.";
		}
		
		if(user!=null)
		{
			convertBlogProxyToEntity.setUser_id(userid);
			convertBlogProxyToEntity.getCategory().setId(categoryId.get().getId());
			System.err.println("------------------"+categoryId.get().getId());
			blogRepo.save(convertBlogProxyToEntity);
		}else {
			// Log or throw an exception if user is not found
			System.err.println("User not found for ID: " + userid);
			return "User not found.";
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
//			blogProxy.getCategory_id().setId(blogEntity.getCategory_id().getId());
			blogRepo.save(blogEntity);
		}
		return "updated successfully";
	}

	//search blog by title
	@Override
	public List<BlogProxy> searchByBlogTitle(String title) {
		List<BlogEntity> byTitle = blogRepo.findByTitle(title);
		return helper.convertList(byTitle,BlogProxy.class);
	}

	@Override
	public List<BlogProxy> getAllBlogs() {
		
		List<BlogEntity> all = blogRepo.findAll();
	
		return helper.convertList(all, BlogProxy.class);
	}
	
	public UserProxy getUserByUserId(Integer id)
	{
	return	userClient.getUserByUserId(id);
	}
	
	//add comment on blog
	@Override // working
	public String addCommentToBlog(Integer blogId,CommentProxy commentProxy)
	{
		Optional<BlogEntity> blogbyId = blogRepo.findById(blogId);
		blogbyId.get().setUser_id(commentProxy.getUserId());
		blogbyId.get().getComments().add(helper.convert(commentProxy, Comment.class));
		blogRepo.save(blogbyId.get());
		return "add comment succefully";
	}
	
	//get comments by Blog id
	@Override
	public List<CommentProxy> getCommentsByBlogId(Integer blogId)
	{
		Optional<BlogEntity> byId = blogRepo.findById(blogId);
//		 List<Comment> byBlogId = commentRepo.findByBlogId(blogId);
		List<Comment> comments = byId.get().getComments();
		return helper.convertList(comments, CommentProxy.class);
		
	}
	
	//get blog by id - working
	@Override
	public BlogProxy getBlogById(Integer id)
	{
		Optional<BlogEntity> byId = blogRepo.findById(id);
		return helper.convert(byId, BlogProxy.class);			
	}

	@Override
	public List<BlogProxy> searchBlogByTitleAndCategory(BlogProxy blogProxy)  {//working
		//get title if exists in db
		List<BlogEntity> byTitle = blogRepo.findByTitle(blogProxy.getTitle());
		
		//get category name if exists in db
		Category byCategoryName = categoryRepo.findByCategoryName(blogProxy.getCategory().getCategoryName());

		//get verify both title and category exist 
//		boolean equals = byTitle.get(0).getCategory().getCategoryName().equals(byCategoryName.getCategoryName());
//		
//		if(equals)
//		{
//		List<BlogProxy> convertList = helper.convertList(byTitle,BlogProxy.class);
//		return convertList;
//		}
		List<BlogProxy> convertList = helper.convertList(byTitle,BlogProxy.class);
		return convertList;
	}

	@Override
	public void createBlogWithImages(String title, String content, Integer userId, Integer categoryId, List<MultipartFile> images) throws IOException {

		Category category = null;
		if (categoryId != null) {
			Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
			if (categoryOptional.isPresent()) {
				category = categoryOptional.get();
			}
		}


		BlogEntity blog = new BlogEntity();
		blog.setTitle(title);
		blog.setContent(content);
		blog.setUser_id(userId);
		blog.setCategory(category);




		// Save Blog First
		BlogEntity savedBlog = blogRepo.save(blog);

		List<BlogImage> blogImages = new ArrayList<>();

		for (MultipartFile image : images) {
			if (!image.isEmpty()) {
				BlogImage blogImage = new BlogImage();
				blogImage.setBlogId(savedBlog.getId());
				blogImage.setImageData(image.getBytes());  // Store in DB as bytes
				blogImages.add(blogImage);
			}
		}

		// Save Images
		blogImageRepo.saveAll(blogImages);
	}

}
