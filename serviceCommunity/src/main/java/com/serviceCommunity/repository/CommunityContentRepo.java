package com.serviceCommunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serviceCommunity.entity.CommunityContent;
import com.serviceCommunity.entity.CommunityMember;
import java.util.List;


@Repository
public interface CommunityContentRepo extends JpaRepository<CommunityContent, Integer>{
	
//	List<CommunityMember> findByCommunityId(Integer communityId);

//	void save(CommunityContent content);

}
