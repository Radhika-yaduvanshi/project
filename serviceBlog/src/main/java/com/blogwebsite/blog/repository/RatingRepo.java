package com.blogwebsite.blog.repository;

import com.blogwebsite.blog.domain.Rating;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Integer> {

    Optional<Rating> findByUserIdAndBlogId(Integer userId,Integer blogId);
//    public Double getAverageRating(Integer blogId) ;
    List<Rating> findByBlogId(Integer blogId);
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.blogId = :blogId")
    Double getAverageRatingByBlogId(@Param("blogId") Integer blogId);
}
