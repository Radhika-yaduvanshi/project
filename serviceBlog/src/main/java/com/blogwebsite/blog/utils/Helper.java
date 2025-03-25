package com.blogwebsite.blog.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {

	@Autowired
	private ObjectMapper mapper;
	
//	public BlogEntity convertBlogProxyToEntity(BlogProxy blogproxy)
//	{
//	return mapper.convertValue(blogproxy, BlogEntity.class);	
//	}
//	
//	public BlogProxy convertBlogEntityToProxy(BlogEntity blogEntity)
//	{
//	return mapper.convertValue(blogEntity, BlogProxy.class);	
//	}
//	
//	public List<BlogEntity> convertBlogListProxyToEntity(List<BlogProxy> blogProxy)
//	{
//	return blogProxy.stream().map(a->mapper.convertValue(a, BlogEntity.class)).toList();	
//	}
//	
//	public List<BlogProxy> convertBlogListEntityToProxy(List<BlogEntity> blogEntitylst)
//	{
//		return blogEntitylst.stream().map(a->mapper.convertValue(a, BlogProxy.class)).toList();
//	}
	
	public <S, T> T convert(S source, Class<T> targetClass) 
	{
	        return mapper.convertValue(source, targetClass);
	}
	   
	public <S,T> List<T> convertList(List<S> sourceList,Class<T> tragetClass)
	{
		return sourceList.stream().map(source->convert(source,tragetClass)).collect(Collectors.toList());
	}
	
}
