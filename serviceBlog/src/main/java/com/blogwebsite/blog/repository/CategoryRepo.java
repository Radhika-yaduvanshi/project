package com.blogwebsite.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.blog.domain.Category;

public interface CategoryRepo extends JpaRepository<com.blogwebsite.blog.domain.Category, Integer>
{
	public Category findByCategoryName(String categoryName);
	
}
