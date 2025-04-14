package com.blogwebsite.blog.domain;

import java.util.List;

import com.blogwebsite.blog.enumeration.BlogStatus;

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
//	@Lob
//	private String titleImage;
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	private Integer views ; // 👈 this tracks the views

	//private String category;
//	@Enumerated(EnumType.STRING)
//	private BlogStatus blogstatus;
	
	//blog have multiple images [ one to many ] - one blog can have multiple images
	//blog category [ one to one] -one blog can relate to one category
	
	private Integer userId;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "blog_id")
//	private List<Category> category;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_id")
	private List<Comment> comments;

//	//for title image
//	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<BlogImage> images;


}
