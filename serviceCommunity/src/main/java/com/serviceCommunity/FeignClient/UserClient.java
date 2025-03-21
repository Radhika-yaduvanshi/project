package com.serviceCommunity.FeignClient;

import com.serviceCommunity.proxy.UserProxy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="UserService",url = "http://localhost:8087/",path = "/user")
public interface UserClient {

	@GetMapping("/getById/{id}")
	public UserProxy getUserByUserId(@PathVariable("id") Integer id);
	
}
