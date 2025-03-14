package com.blogwebsite.admin.feingClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blogwebsite.admin.proxy.BlogProxy;


@FeignClient(name = "BlogService",url = "http://localhost:8088/",path = "blog")
public interface BlogClient {
	

	
	//Blogs
	
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

}


//@FeignClient(name="UserService",url = "http://localhost:8087/",path = "/user")
//public interface UserClient {
//
//	@GetMapping("/getById/{id}")
//	public UserProxy getUserByUserId(@PathVariable("id") Integer id);
//	
//}

