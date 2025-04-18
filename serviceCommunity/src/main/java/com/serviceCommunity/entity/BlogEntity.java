package com.serviceCommunity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	private Integer userId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id") // foreign key colum
	private Community community;

	//private String category;
//	@Enumerated(EnumType.STRING)
//	private BlogStatus blogstatus;
	
	//blog have multiple images [ one to many ] - one blog can have multiple images
	//blog category [ one to one] -one blog can relate to one category
	

	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "blog_id")
//	private List<Category> category;
	
//	@ManyToOne
//	@JoinColumn(name = "category_id")
//	private Category category;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "blog_id")
//	private List<Comment> comments;

//	//for title image
//	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<BlogImage> images;


}
