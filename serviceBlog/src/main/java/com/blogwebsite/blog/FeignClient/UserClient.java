package com.blogwebsite.blog.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogwebsite.blog.proxy.UserProxy;

@FeignClient(name="UserService",url = "http://localhost:8087/",path = "/user")
public interface UserClient {

	@GetMapping("/getById/{id}")
	public UserProxy getUserByUserId(@PathVariable("id") Integer id);
	
}
