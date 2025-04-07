package com.blogwebsite.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.user.domain.UserEntity;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Integer>
{
	public UserEntity findByUserName(String name);
	public UserEntity findByEmail(String email);  // For email-based lookup during registration

}
