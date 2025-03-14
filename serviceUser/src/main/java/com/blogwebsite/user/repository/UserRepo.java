package com.blogwebsite.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.user.domain.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>
{
	public UserEntity findByUserName(String name);
}
