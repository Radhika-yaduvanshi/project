package com.blogwebsite.blog.domain;

import com.blogwebsite.blog.enumeration.BlogStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	@Enumerated(EnumType.STRING)
	private BlogStatus blogstatus;
	
	//blog have multiple images [ one to many ] - one blog can have multiple images
	//blog category [ one to one] -one blog can relate to one category
	
	private Integer user_id;
	
}
