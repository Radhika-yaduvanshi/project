package com.blogwebsite.blog.repository;

import com.blogwebsite.blog.domain.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogImageRepo extends JpaRepository<BlogImage,Integer> {

    Optional<BlogImage> findFirstByBlogIdAndIsMainTrue(Integer blogId);
}
