package com.blogwebsite.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.blog.domain.Comment;


public interface CommentRepo extends JpaRepository<Comment, Integer>
{
//
//	List<Comment> findByBlogId(Optional<BlogEntity> byId);
//
//	List<Comment> findByBlogId(Integer blogId);
//
	
//	void findByBlogId(Integer id);
	
}
