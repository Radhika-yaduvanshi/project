package com.serviceCommunity.entity;

import com.serviceCommunity.enums.MemberStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer communityMemberId;
//	private Integer communityId;
	private Integer uid;
	@Enumerated(EnumType.STRING)
	private MemberStatus status;
	@ManyToOne
	@JoinColumn(name = "community_id", nullable = false)
	private Community community;
	

}
