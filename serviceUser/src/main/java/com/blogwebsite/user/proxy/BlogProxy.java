package com.blogwebsite.user.proxy;

import com.blogwebsite.user.enumeration.BlogStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogProxy {

	private String title;
	private String content;
	
	private BlogStatus blogstatus;
}
