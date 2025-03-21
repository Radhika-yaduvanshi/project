package com.serviceCommunity.service;

import java.util.List;

import com.serviceCommunity.entity.Community;
import com.serviceCommunity.proxy.CommunityContentProxy;
import com.serviceCommunity.proxy.CommunityProxy;

public interface CommunityService {
	
    CommunityProxy createCommunity(CommunityProxy communityProxy,Integer uid);

    List<CommunityProxy> getAllCommunity();
    CommunityProxy getCommunityByName(String name);
    CommunityProxy getCommunityById(Integer communityId);
    String UpdateCommunity(Integer id,CommunityProxy communityProxy);
    String DeleteCommunity(Integer id);
    List<CommunityProxy> getCommunityByUserId(Integer id);



}
