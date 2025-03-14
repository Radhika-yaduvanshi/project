package com.blogwebsite.admin.domain;

import com.blogwebsite.admin.enumeration.GenderEnum;
import com.blogwebsite.admin.enumeration.RoleEnum;

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
@Table(name="admin")
public class AdminEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;
}
