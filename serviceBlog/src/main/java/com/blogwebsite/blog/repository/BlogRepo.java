package com.blogwebsite.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.blog.domain.BlogEntity;

public interface BlogRepo extends JpaRepository<BlogEntity,Integer>
{
	List<BlogEntity> findByTitle(String title);
	List<BlogEntity> findByUserId(Integer id);
	
//	List<BlogEntity> findByComments(List<Comment> comments);

}
