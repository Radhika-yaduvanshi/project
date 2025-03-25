package com.blogwebsite.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.repository.CommentRepo;
import com.blogwebsite.blog.service.CommentService;
import com.blogwebsite.blog.utils.Helper;

@Service
public class CommentServiceImpl implements CommentService
{
	@Autowired
	private CommentRepo commentRepo;
	
//	@Autowired
//	private BlogRepo blogRepo;
	
	@Autowired
	private Helper helper;

//	@Autowired
//	private BlogServiceImpl blogServiceImpl;
	
	@Override
	public String deleteComment(Integer id) {
		commentRepo.deleteById(id);
		return "Comment deleted successfully";
	}
	
	@Override
	public CommentProxy updateCommentProxy(Integer id, CommentProxy commentProxy) {
		return null;
	}

	@Override
	public List<CommentProxy> getAllComments()
	{
		return helper.convertList(commentRepo.findAll(), CommentProxy.class);
	}

}
