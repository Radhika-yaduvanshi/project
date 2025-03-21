package com.serviceCommunity.repository;

import java.util.List;
import java.util.Optional;

import com.serviceCommunity.entity.Community;
import com.serviceCommunity.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serviceCommunity.entity.CommunityMember;


@Repository
public interface CommunityMembersRepo extends JpaRepository<CommunityMember, Integer>{
	
//	Optional<CommunityMember> findByCommunityIdAndUid(Integer communityId, Integer uid);
//    boolean findByCommunityIdAndUid(Integer communityId, Integer uid);
     boolean existsByCommunityAndUid(Community community, Integer uid);
     List<CommunityMember> findByCommunityOwnerIdAndStatus(Integer ownerId, MemberStatus memberStatus);
}
