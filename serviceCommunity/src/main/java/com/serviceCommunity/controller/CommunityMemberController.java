package com.serviceCommunity.controller;


import com.serviceCommunity.proxy.CommunityMemberProxy;
import com.serviceCommunity.serviceImpl.CommunityMemberServiceImpl;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communityMember")
public class CommunityMemberController {

    @Autowired
    private CommunityMemberServiceImpl communityMemberService;
    @PostMapping("/request/{uid}/{communityId}")
    String sendJoinRequest(@PathVariable("communityId") Integer communityId,@PathVariable("uid") Integer uid){
        return communityMemberService.sendJoinRequest(communityId,uid);
    }
    @PostMapping("/handle/{memberId}/{action}")
    String handleJoinRequest(@PathVariable("memberId")Integer memberId,@PathVariable("action") String action){
        return communityMemberService.handleJoinRequest(memberId,action);
    } // Approve/Reject
    @GetMapping("/getcommunityMembers")
    List<CommunityMemberProxy> getCommunityMembers(){
        return  communityMemberService.getCommunityMembers();
    }
    @GetMapping("/pending/{ownerId}")
    public List<CommunityMemberProxy> getPendingRequests(@PathVariable("ownerId") Integer ownerId){
        return  communityMemberService.getPendingRequests(ownerId);
    }
}
