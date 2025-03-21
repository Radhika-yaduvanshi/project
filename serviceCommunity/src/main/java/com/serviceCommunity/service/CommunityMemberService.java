package com.serviceCommunity.service;

import com.serviceCommunity.entity.CommunityMember;
import com.serviceCommunity.proxy.CommunityMemberProxy;

import java.util.List;

public interface CommunityMemberService {

    String sendJoinRequest(Integer communityId, Integer uid);

    String handleJoinRequest(Integer memberId, String action); // Approve/Reject

    List<CommunityMemberProxy> getCommunityMembers();
    public List<CommunityMemberProxy> getPendingRequests(Integer ownerId);
}
