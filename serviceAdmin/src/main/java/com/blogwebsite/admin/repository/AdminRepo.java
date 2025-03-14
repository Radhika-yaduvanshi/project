package com.blogwebsite.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogwebsite.admin.domain.AdminEntity;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity,Long>
{
	public List<AdminEntity> findByName(String name);
}
