package com.blogwebsite.blog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_tbl")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String comment_content;
	
	private Integer userId;

//	
//	@ManyToOne
//	@JoinColumn(name = "blog_id")
//	private BlogEntity blog;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "comment_tbl_id")
//	private List<BlogEntity> blog;
	
}
