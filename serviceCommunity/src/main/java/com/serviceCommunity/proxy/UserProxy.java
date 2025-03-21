package com.serviceCommunity.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
	
//	private List<BlogProxy> blog;
}
