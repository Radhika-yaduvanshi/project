package com.blogwebsite.blog.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "LONGBLOB") // To store the image as binary data
    private byte[] imageData;

    private String imagePath; // Optional: store the file path if saving locally

    @Column(name = "blog_id") // Foreign key to blog
    private Integer blogId;

}
