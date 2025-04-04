package com.blogwebsite.user.proxy;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProxy {

	private Integer id;
	private String userName;
	private String password;
	private String email;
	
	private Date createdAt;

	private Date updateAt;
	
	private byte[] profilePhoto;
	private String role;

//private String profilePhoto;
}
