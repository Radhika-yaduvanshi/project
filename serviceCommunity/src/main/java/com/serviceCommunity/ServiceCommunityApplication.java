package com.serviceCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCommunityApplication.class, args);
	}

}
