package com.blogwebsite.user.domain;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

	@Lob
	private byte[] profilePhoto;

	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'USER'", nullable = false)
	private String role;

	@Column(name = "otp")
	private String otp;

	@Column(name = "otp_requested_time")
	private LocalDateTime otpRequestedTime;

//	private String profilePhoto;
	
	//@OneToMany(cascade=CascadeType.ALL)
	//@JoinColumn(name="user_id")
	//private List<BlogEntity> blog;
}
