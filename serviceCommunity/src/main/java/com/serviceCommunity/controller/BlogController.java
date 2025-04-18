package com.serviceCommunity.controller;

import com.serviceCommunity.entity.BlogEntity;
import com.serviceCommunity.proxy.BlogProxy;
import com.serviceCommunity.repository.BlogRepo;
import com.serviceCommunity.serviceImpl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/community")
public class BlogController {

	@Autowired
	private BlogServiceImpl blogImpl;

	@Autowired
	private BlogRepo blogRepo;


//	//titile image
//	@GetMapping("title-image/{blogId}")
//	public BlogImage getTitleImage(@PathVariable("blogId") Integer blogId){
//		return blogImpl.getMainImageForBlog(blogId);
//	}

	@GetMapping("getBlogByCommunityId/{communityId}")
	public List<BlogProxy> getBlogByCommunityId(@PathVariable("communityId") Integer communityId) {
		return blogImpl.getBlogByCommunityId(communityId);
	}

	@PutMapping("/{id}/view")
	public ResponseEntity<?> incrementViews(@PathVariable("id") Integer id) {
		Optional<BlogEntity> blogOpt = blogRepo.findById(id);

		if (blogOpt.isPresent()) {
			BlogEntity blog = blogOpt.get();
			if (blog.getViews() == null) {
				blog.setViews(0);}
			blog.setViews(blog.getViews() + 1); // 👈 Increment view
			blogRepo.save(blog);          // Save updated blog

			return ResponseEntity.ok(blog);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
		}
	}
	@GetMapping("/most-viewed-blogs")
	public ResponseEntity<List<BlogEntity>> getmostViewedBlogs() {
		// Fetch blogs sorted by views in descending order
		List<BlogEntity> blogs = blogRepo.findAll(Sort.by(Sort.Order.desc("views")));
		return ResponseEntity.ok(blogs);
	}



	@GetMapping("/getBlogByUserId/{userId}")
	public List<BlogProxy> getBlogByUserId(@PathVariable("userId") Integer userId){
		return blogImpl.getBlogByUserId(userId);
	}
	
	//create blog
	@PostMapping("/save/{id}/{communityId}") //working -user
	public ResponseEntity<?> saveBlog(@RequestBody BlogProxy blogProxy,@PathVariable("id") Integer id,@PathVariable("id") Integer communityId)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.createBlog(blogProxy,id,communityId));
	}

//	@PostMapping("/save") //working -user
//	public ResponseEntity<?> saveBlog(@RequestBody BlogProxy blogProxy)
//	{
//		System.err.println("here in controller of blog");
//		System.err.println("details of blogproxy : "+blogProxy.getUser_id());
//		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.createBlog(blogProxy));
//	}
	
	//delete blog
	@DeleteMapping("/delete/{id}") //working -user
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.deleteBlog(id));
	}
	
	//update blog
	@PutMapping("/update/{id}")  //working -user
	public ResponseEntity<?> updateBlog(@RequestBody BlogProxy blogProxy,@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.updateBlog(blogProxy, id));
	}
	
	//search blog by title 
	@GetMapping("/searchBlogByTitle/{title}") //working -user
	public ResponseEntity<?> searchBlogByTitle(@PathVariable("title") String title)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.searchByBlogTitle(title));
	}
	
	//get all blogs
	@GetMapping("/getAllBlogs") //working -user
	public ResponseEntity<?> getAllBlog()
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getAllBlogs());
	}
	
	
	//get user by id for user id reference 
	@GetMapping("/getUserById/{id}") //working - to store user id in blog table
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getUserByUserId(id));
	}
	
//	//add comment on blog - working
//	@PostMapping("/AddComment/{id}")
//	public ResponseEntity<?> addComment(@PathVariable("id") Integer id,@RequestBody CommentProxy commentProxy)
//	{
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.addCommentToBlog(id, commentProxy));
//	}
//
	
	//get only comment by blog id - working
//	@GetMapping("/getCommentsByBlogId/{id}")
//	public ResponseEntity<?> getCommentsByBlogId(@PathVariable("id") Integer id)
//	{
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getCommentsByBlogId(id));
//	}
	

	//get blog by id - working
	@GetMapping("/getBlogById/{id}") //show all comments on blog by id
	public ResponseEntity<?> getBlogById1(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getBlogById(id));
	}
	
	@PostMapping("/searchBlogByTitleAndCategory") //working [added- 22-03-2025]
	public ResponseEntity<?> searchByBlogTitleAndCategoryName(@RequestBody BlogProxy blogProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(blogImpl.searchBlogByTitleAndCategory(blogProxy));
	}
}
