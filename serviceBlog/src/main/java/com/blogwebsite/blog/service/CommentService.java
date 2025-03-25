package com.blogwebsite.blog.service;

import java.util.List;

import com.blogwebsite.blog.proxy.CommentProxy;

public interface CommentService {

	
	//delete comment
	public String deleteComment(Integer id);
	
	//update comment
	public CommentProxy updateCommentProxy(Integer id,CommentProxy commentProxy);
	
	public List<CommentProxy> getAllComments();

}
