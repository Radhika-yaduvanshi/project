package com.blogwebsite.user.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogwebsite.user.proxy.BlogProxy;

@FeignClient(name = "CategoryService",path = "category",url = "http://localhost:8088/")
public interface CategoryClient {

	@GetMapping("/searchBlogByCategory/{category}")
	public List<BlogProxy> searchBlogByCategory(@PathVariable("category") String category);
	
}
