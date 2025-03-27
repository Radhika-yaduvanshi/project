package com.blogwebsite.blog.domain;

import java.util.List;

import com.blogwebsite.blog.enumeration.BlogStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="blog")
public class BlogEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String content;
	//private String category;
//	@Enumerated(EnumType.STRING)
//	private BlogStatus blogstatus=BlogStatus.pending;;
	
	//blog have multiple images [ one to many ] - one blog can have multiple images
	//blog category [ one to one] -one blog can relate to one category
	
	private Integer user_id;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "blog_id")
//	private List<Category> category;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_id")
	private List<Comment> comments;

	@OneToMany(mappedBy = "blogId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<BlogImage> images;

}
