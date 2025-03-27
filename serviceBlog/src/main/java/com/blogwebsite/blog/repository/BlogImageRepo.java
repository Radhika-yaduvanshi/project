package com.blogwebsite.blog.repository;

import com.blogwebsite.blog.domain.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogImageRepo extends JpaRepository<BlogImage,Long> {
}
