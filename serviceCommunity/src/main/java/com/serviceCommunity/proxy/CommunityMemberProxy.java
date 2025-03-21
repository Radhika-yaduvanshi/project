package com.serviceCommunity.proxy;

import com.serviceCommunity.entity.Community;
import com.serviceCommunity.enums.MemberStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityMemberProxy {


	private Integer communityMemberId;

	private Integer uid;

	private MemberStatus status;

	private Community community;
}
