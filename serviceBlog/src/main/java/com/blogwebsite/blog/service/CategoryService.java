package com.blogwebsite.blog.service;

import java.util.List;

import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CategoryProxy;

public interface CategoryService {

	//create category
	public String createCategory(CategoryProxy categoryProxy);
	
	//delete category
	public String deleteCategory(Integer id);
	
	//update category
	public CategoryProxy updateCategoryProxy(CategoryProxy categoryProxy,Integer id);
	
	//search by category name
	public List<BlogProxy> searchByCategory(String categoryName);
	
	public List<CategoryProxy> getAllCategory();
	
}
