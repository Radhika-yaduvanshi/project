package com.blogwebsite.user.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
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
@Table(name="user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String userName;
	private String password;
	private String email;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updateAt;
	
	private byte[] profilePhoto;
	
	//@OneToMany(cascade=CascadeType.ALL)
	//@JoinColumn(name="user_id")
	//private List<BlogEntity> blog;
}
