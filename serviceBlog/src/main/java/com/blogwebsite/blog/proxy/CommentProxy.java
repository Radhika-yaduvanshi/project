package com.blogwebsite.blog.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentProxy {

//	private Integer id;
	private String comment_content;

	private Integer userId;
	
//	private BlogProxy blog;
	
}
