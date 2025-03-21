package com.serviceCommunity.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {

	@Autowired
	private ObjectMapper mapper;
	   
	public <S, T> T convert(S source, Class<T> targetClass) 
	{
	        return mapper.convertValue(source, targetClass);
	}
	   
	public <S,T> List<T> convertList(List<S> sourceList,Class<T> tragetClass)
	{
		return sourceList.stream().map(source->convert(source,tragetClass)).collect(Collectors.toList());
	}

}
