package com.blogwebsite.admin.proxy;

import com.blogwebsite.admin.enumeration.GenderEnum;
import com.blogwebsite.admin.enumeration.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProxy {

	private String name;
	private String password;
	private RoleEnum role;
	private GenderEnum gender;
}
