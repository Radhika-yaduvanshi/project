package com.blogwebsite.admin.feingClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blogwebsite.admin.proxy.AdminProxy;
import com.blogwebsite.admin.proxy.UserProxy;


@FeignClient(name="UserService",url = "http://localhost:8087/",path = "/user")

public interface UserClient {
	
	@GetMapping("/getById/{id}")
	public UserProxy getUserByUserId(@PathVariable("id") Integer id);
	
	
	//working register user
	@PostMapping("/register") //user & admin 
	public ResponseEntity<?> registerUser(@RequestBody UserProxy user);
	
	//working -delete user by id
	@DeleteMapping("/deleteById/{id}") //admin
	public String deleteById(@PathVariable("id") Integer id);
	
	
	//working - get all users
	@GetMapping("/getAll") //admin 
	public List<UserProxy> getAllUser();
	
	//working -get user by usernamme
	@GetMapping("/getByUserName/{uname}") //admin
	public UserProxy getUserByUserName(@PathVariable("uname") String userName);

	
}
