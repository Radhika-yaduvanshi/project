package com.serviceCommunity.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogProxy {

	private Integer id;
	private String title;
	private String content;
	private Integer views ;

//	private BlogStatus blogstatus;
	
	private Integer userId;
	
//	private CategoryProxy category;
//
//	private List<CommentProxy> comments;
	
}
