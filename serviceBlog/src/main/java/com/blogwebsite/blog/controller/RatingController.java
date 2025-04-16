package com.blogwebsite.blog.controller;


import com.blogwebsite.blog.domain.Rating;
import com.blogwebsite.blog.service.impl.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingServiceImpl ratingService;

    @PostMapping
    public ResponseEntity<Rating> rateBlog( @RequestParam Integer userId,
                                            @RequestParam Integer blogId,
                                            @RequestParam Integer ratingValue
    ){
        if(ratingValue <1 || ratingValue >5){
            return  ResponseEntity.badRequest().build();
        }

        Rating rating=ratingService.rateBloge(userId,blogId,ratingValue);
        return ResponseEntity.ok(rating);
    }

    // Get average rating
    @GetMapping("/average/{blogId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Integer blogId) {
        Double avg = ratingService.getAvrgRating(blogId);
        return ResponseEntity.ok(avg != null ? avg : 0.0);
    }
    // Get all ratings for a blog
    @GetMapping("/blog/{blogId}")
    public List<Rating> getRatings(@PathVariable Integer blogId) {
        return ratingService.getRatingsforBlog(blogId);
    }


}
