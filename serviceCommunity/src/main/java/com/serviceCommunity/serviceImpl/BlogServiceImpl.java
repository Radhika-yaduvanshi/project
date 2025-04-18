package com.serviceCommunity.serviceImpl;


import com.serviceCommunity.FeignClient.UserClient;
import com.serviceCommunity.entity.BlogEntity;
import com.serviceCommunity.entity.Community;
import com.serviceCommunity.proxy.BlogProxy;
import com.serviceCommunity.proxy.UserProxy;
import com.serviceCommunity.repository.BlogRepo;
import com.serviceCommunity.repository.CommunityRepo;
import com.serviceCommunity.service.BlogService;
import com.serviceCommunity.util.Helper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService
{
	@Autowired
	private EntityManager entityManager;

	
	
	@Autowired
	private BlogRepo blogRepo;

//	@Autowired
//	private CategoryRepo categoryRepo;
	
	@Autowired
	private Helper helper;

	
	@Autowired
	private UserClient userClient;
	@Autowired
	private CommunityRepo communityRepo;


	
	@Override
	public String createBlog(BlogProxy blogproxy, Integer id,Integer communityId) {

		System.out.println("User id is : "+id);
		BlogEntity convertBlogProxyToEntity = helper.convert(blogproxy, BlogEntity.class);

//		Optional<Category> categoryId = categoryRepo.findById(blogproxy.getCategory().getId());

//		System.err.println(categoryId);
//		UserProxy user = userClient.getUserByUserId(userid);
		// 2. Validate Community
		Optional<Community> communityOpt = communityRepo.findById(communityId);
		if (communityOpt.isEmpty()) {
			throw new RuntimeException("Community not found with ID: " + communityId);
		}

		UserProxy user = userClient.getUserByUserId(id);
		convertBlogProxyToEntity.setCommunity(communityOpt.get());
		System.out.println("user details : "+user.getUserName()+" "+user.getEmail()+" ");
		if(user!=null)
		{
//			convertBlogProxyToEntity.setUser_id(email);
			convertBlogProxyToEntity.setUserId(user.getId());
//			BlogEntity.  (communityOpt.get()); // set community object

//			convertBlogProxyToEntity.getCategory().setId(categoryId.get().getId());
//			System.err.println("------------------"+categoryId.get().getId());
			blogRepo.save(convertBlogProxyToEntity);
		}
		return "saved successfully";
	}
//@Override
////@Transactional
//public String createBlog(BlogProxy blogproxy) {
//// Get the authenticated user's email (from the SecurityContext)
////	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////	// Get the authenticated user's email from the SecurityContext
////	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////	if (authentication == null) {
////		return "User is not authenticated";
////	}
////
//
////	System.out.println("email of user is : "+email+"====================================");
//
//	// Fetch the user details based on email (You can replace this with your own user service)
//	UserProxy user = userClient.UserByEmail(email);
//	System.out.println("email of user is : "+user);
//	if (user == null) {
//		return "User not found!";
//	}
//
//	// Convert BlogProxy to BlogEntity
//	BlogEntity blogEntity = new BlogEntity();
//	blogEntity.setTitle(blogproxy.getTitle());
//	blogEntity.setContent(blogproxy.getContent());
//
//	// Get the category
//	Category category = categoryRepo.findById(blogproxy.getCategory().getId()).orElse(null);
//	if (category != null) {
//		blogEntity.setCategory(category);
//	} else {
//		return "Category not found!";
//	}
//
//	// Set the user_id of the authenticated user in the blog
//	blogEntity.setUser_id(user.getId()); // Assuming user proxy has getId()
//
//	// Save the blog
//	blogRepo.save(blogEntity);
//
//	return "Blog created successfully!";
//
//}



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
//		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getAllBlogs",BlogEntity.class);

		List<BlogEntity> all = blogRepo.findAll();
//		List<BlogEntity> all = query.getResultList();
	
		return helper.convertList(all, BlogProxy.class);
	}
	
	public UserProxy getUserByUserId(Integer id)
	{

	return	userClient.getUserByUserId(id);
	}
	
	//add comment on blog
//	@Override // working
//	public String addCommentToBlog(Integer blogId,CommentProxy commentProxy)
//	{
//		Optional<BlogEntity> blogbyId = blogRepo.findById(blogId);
//		blogbyId.get().setUserId(commentProxy.getUserId());
//		blogbyId.get().getComments().add(helper.convert(commentProxy, Comment.class));
//		blogRepo.save(blogbyId.get());
//		return "add comment succefully";
//	}
	
	//get comments by Blog id
//	@Override
//	public List<CommentProxy> getCommentsByBlogId(Integer blogId)
//	{
//		Optional<BlogEntity> byId = blogRepo.findById(blogId);
////		 List<Comment> byBlogId = commentRepo.findByBlogId(blogId);
//		List<Comment> comments = byId.get().getComments();
//		return helper.convertList(comments, CommentProxy.class);
//
//	}
	
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
//		Category byCategoryName = categoryRepo.findByCategoryName(blogProxy.getCategory().getCategoryName());

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
	public String createBlog(BlogProxy blogproxy) {
		return "";
	}

	@Override
	public List<BlogProxy> getBlogByUserId(Integer id) {

		UserProxy user = userClient.getUserByUserId(id);

		Integer userId=user.getId();
		System.out.println("user id is : "+userId);

		List<BlogEntity> blog = blogRepo.findByUserId(userId);


		return helper.convertList(blog,BlogProxy.class);
	}

	@Override
	public List<BlogProxy> getBlogByCommunityId(Integer communityId) {
		 ;

		List<BlogProxy> blogByCommunity = helper.convertList(blogRepo.findByCommunity_CommunityId(communityId),BlogProxy.class);
		return blogByCommunity;
	}


//	//for title image
//	public BlogImage getMainImageForBlog(Integer blogId) {
//		return blogImageRepo.findFirstByBlogIdAndIsMainTrue(blogId)
//				.orElse(null);
//	}

}
