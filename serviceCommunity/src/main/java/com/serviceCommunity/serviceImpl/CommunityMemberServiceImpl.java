package com.serviceCommunity.serviceImpl;

import com.serviceCommunity.FeignClient.UserClient;
import com.serviceCommunity.entity.Community;
import com.serviceCommunity.entity.CommunityMember;
import com.serviceCommunity.enums.MemberStatus;
import com.serviceCommunity.proxy.CommunityMemberProxy;
import com.serviceCommunity.proxy.UserProxy;
import com.serviceCommunity.repository.CommunityMembersRepo;
import com.serviceCommunity.repository.CommunityRepo;
import com.serviceCommunity.service.CommunityMemberService;
import com.serviceCommunity.util.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommunityMemberServiceImpl implements CommunityMemberService {

    @Autowired
    private CommunityMembersRepo communityMembersRepo;
    @Autowired
    private CommunityRepo communityRepo;
    @Autowired
    private UserClient userClient;
    @Autowired
    private Helper helper;



    @Override
    @Transactional
    public String sendJoinRequest( Integer communityId, Integer uid) {

      Optional<Community> communityOpt= communityRepo.findById(communityId);
        if (!communityOpt.isPresent()) {
            return "Error: Community not found!";
        }

        Community community = communityOpt.get();
        if (community.getOwnerId().equals(uid)) {
            return "You are the owner. No need to join.";
        }
      UserProxy user = userClient.getUserByUserId(uid);
        uid=user.getId();
       String  uname=user.getUserName();
        System.err.println(uid+"This is user");
        System.err.println(uname+"This is userName");

        Boolean existingRequest = communityMembersRepo.existsByCommunityAndUid(community,uid);
        System.err.println(existingRequest+"is user with community and uid exists or not");
        if(existingRequest){
            return  "You have already requested to join this community.";
        }
          CommunityMember communityMember = new CommunityMember();
          communityMember.setUid(uid);
//          communityMember.setCommunityMemberId(uid);
          communityMember.setStatus(MemberStatus.PENDING);
          communityMember.setCommunity(community);
          communityMember= communityMembersRepo.save(communityMember);
          helper.convert(communityMember,CommunityMemberProxy.class);

        return "Request to join community sent!";
    }

    @Override
    public String handleJoinRequest(Integer memberId, String action) {

       Optional<CommunityMember>memberOpt = communityMembersRepo.findById(memberId);

        if (!memberOpt.isPresent()) {
            return "Error: Community Member with ID " + memberId + " not found!";
        }

       CommunityMember communityMember = memberOpt.get();
       if("APPROVE".equalsIgnoreCase(action)){
           communityMember.setStatus(MemberStatus.APPROVED);
       }
        if("PENDING".equalsIgnoreCase(action)){
            communityMember.setStatus(MemberStatus.PENDING);
        }
        if("REGECTED".equalsIgnoreCase(action)){
            communityMember.setStatus(MemberStatus.REGECTED);
        }
        communityMembersRepo.save(communityMember);
        return "Membership Request "+action.toLowerCase()+"d  Successfully!";
    }

    @Override
    public List<CommunityMemberProxy> getCommunityMembers() {

       List<CommunityMember>communityMembers= communityMembersRepo.findAll();
     return   helper.convertList(communityMembers,CommunityMemberProxy.class);
    }

    @Override
    public List<CommunityMemberProxy> getPendingRequests(Integer ownerId) {
        List<CommunityMember> pendingRequests = communityMembersRepo.findByCommunityOwnerIdAndStatus(ownerId, MemberStatus.PENDING);
        return helper.convertList(pendingRequests, CommunityMemberProxy.class);
    }
}
