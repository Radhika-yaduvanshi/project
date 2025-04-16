package com.blogwebsite.blog.service;

import com.blogwebsite.blog.domain.Rating;

import java.util.List;

public interface RatingService {

    public Rating  rateBloge(Integer userId,Integer blogId,Integer ratingValue);
    public Double getAvrgRating(Integer blogId);
    public List<Rating> getRatingsforBlog(Integer blogId);
}
