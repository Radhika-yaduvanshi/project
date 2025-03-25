package com.blogwebsite.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.domain.Category;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CategoryProxy;
import com.blogwebsite.blog.repository.BlogRepo;
import com.blogwebsite.blog.repository.CategoryRepo;
import com.blogwebsite.blog.service.CategoryService;
import com.blogwebsite.blog.utils.Helper;

@Service
public class CategoryServiceImpl implements CategoryService
{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private BlogRepo blogRepo;
	
	@Autowired
	private Helper helper;
	
	@Override
	public String createCategory(CategoryProxy categoryProxy) {
		categoryRepo.save(helper.convert(categoryProxy, Category.class));
		return null;
	}

	
	
	@Override
	public String deleteCategory(Integer id) {
		categoryRepo.deleteById(id);
		return null;
	}

	@Override
	public CategoryProxy updateCategoryProxy(CategoryProxy categoryProxy, Integer id) {

		return null;
	}

	@Override
	public List<BlogProxy> searchByCategory(String categoryName) {
		
		//get category by name
		Category findBYCategoryName = categoryRepo.findByCategoryName(categoryName);
		
		//get category id through category name
		Integer categoryId = findBYCategoryName.getId();
		
		//find all blogs 
		List<BlogEntity> findallBlog = blogRepo.findAll();
	
		List<BlogEntity> Bloglist = findallBlog.stream().filter(a->a.getCategory().getId().equals(categoryId)).toList();
		
		return helper.convertList(Bloglist, BlogProxy.class);
	}



	@Override
	public List<CategoryProxy> getAllCategory() {
		return helper.convertList(categoryRepo.findAll(), CategoryProxy.class);
	}

}
