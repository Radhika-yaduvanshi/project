package com.blogwebsite.user.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;

@FeignClient(name = "BlogService",url = "http://localhost:8088/",path = "blog")
public interface BlogClient {
	
	
	@GetMapping("/searchBlogByTitle/{title}") //working
	public List<BlogProxy> searchBlogByTitle(@PathVariable("title") String title);
	
	@PostMapping("/save/{id}")
	public String createBlog(@RequestBody BlogProxy blogproxy,@PathVariable("id") Integer userid); //user
	
	@DeleteMapping("/delete/{id}")
	public String deleteBlog(@PathVariable("id") Integer id);
	
	@PutMapping("/update/{id}")
	public String updateBlog(@RequestBody BlogProxy blogProxy,@PathVariable("id") Integer id);
	
	@GetMapping("/getAllBlogs") //working
	public List<BlogProxy> getAllBlogs();
	
	@PostMapping("/AddComment/{id}")
	public String addComment(@PathVariable("id") Integer id,@RequestBody CommentProxy commentProxy);
	
	@PostMapping("/searchBlogByTitleAndCategory") //working [added- 22-03-2025]
	public List<BlogProxy> searchByBlogTitleAndCategoryName(@RequestBody BlogProxy blogProxy);

}
