package com.blogwebsite.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BlogImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl; // If you're storing path/URL

    @Lob
    private byte[] imageData; // If storing binary (optional)

    private boolean isMain; // Marks this as the title image

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private BlogEntity blog;


}
