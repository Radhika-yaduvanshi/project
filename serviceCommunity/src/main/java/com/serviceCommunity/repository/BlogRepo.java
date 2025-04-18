package com.serviceCommunity.repository;


import com.serviceCommunity.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepo extends JpaRepository<BlogEntity,Integer>
{
	List<BlogEntity> findByTitle(String title);
	List<BlogEntity> findByUserId(Integer id);
	// In CommunityPostRepository
	List<BlogEntity> findByCommunity_CommunityId(Integer communityId);



//	List<BlogEntity> findByComments(List<Comment> comments);

}
