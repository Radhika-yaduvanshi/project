package com.blogwebsite.blog.service.impl;

import com.blogwebsite.blog.FeignClient.UserClient;
import com.blogwebsite.blog.domain.Rating;
import com.blogwebsite.blog.proxy.UserProxy;
import com.blogwebsite.blog.repository.RatingRepo;
import com.blogwebsite.blog.service.RatingService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private UserClient userClient;
    @Override
    public Rating rateBloge(Integer userId, Integer blogId,Integer ratingValue) {

       UserProxy user = userClient.getUserByUserId(userId);
//       Integer userid =user.getId();
       if(user==null ||user.getId()==null){
           throw new IllegalArgumentException("Invalid user ID: " + userId);
       }

        Optional<Rating> existing=  ratingRepo.findByUserIdAndBlogId(userId,blogId);
        Rating rating;
        if(existing.isPresent()){
            rating=existing.get();
            rating.setRatingValue(ratingValue);
        }else {
            rating = new Rating();
            rating.setUserId(userId);
            rating.setBlogId(blogId);
            rating.setRatingValue(ratingValue);
            rating.setCreatedAt(LocalDateTime.now());
        }

        return  ratingRepo.save(rating);
    }

    @Override
    public Double getAvrgRating(Integer blogId) {
        return ratingRepo.getAverageRatingByBlogId(blogId);
    }

    @Override
    public List<Rating> getRatingsforBlog(Integer blogId) {
        return ratingRepo.findByBlogId(blogId);
    }
}
