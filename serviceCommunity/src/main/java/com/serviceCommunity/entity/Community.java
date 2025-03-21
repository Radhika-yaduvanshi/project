package com.serviceCommunity.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer communityId;
	private String title;
	private String description;
	private Integer ownerId;//user id
	private Boolean isActive;
	@CreationTimestamp
	private LocalDateTime createdAt;
//	@OneToOne(mappedBy = "community", cascade = CascadeType.ALL)
//	private CommunityContent communityContent;
//
//	@OneToMany(mappedBy = "community")
//	private List<CommunityMember> communityMembers;

}
