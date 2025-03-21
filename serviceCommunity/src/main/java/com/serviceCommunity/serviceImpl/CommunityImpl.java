package com.serviceCommunity.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.serviceCommunity.FeignClient.UserClient;
import com.serviceCommunity.proxy.UserProxy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serviceCommunity.entity.Community;
import com.serviceCommunity.entity.CommunityContent;
import com.serviceCommunity.entity.CommunityMember;
import com.serviceCommunity.enums.MemberStatus;
import com.serviceCommunity.proxy.CommunityContentProxy;
import com.serviceCommunity.proxy.CommunityProxy;
import com.serviceCommunity.repository.CommunityContentRepo;
import com.serviceCommunity.repository.CommunityMembersRepo;
import com.serviceCommunity.repository.CommunityRepo;
import com.serviceCommunity.service.CommunityService;
import com.serviceCommunity.util.Helper;

@Service
public class CommunityImpl implements CommunityService{
	@Autowired
	private CommunityRepo communityRepo;
	@Autowired
	private CommunityContentRepo communitycontentrepo;
	@Autowired
	private CommunityMembersRepo communityMembersRepo;

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private Helper helper;

	@Override
	public CommunityProxy createCommunity(CommunityProxy communityProxy,Integer uid) {

//		Community community1=helper.convert(communityProxy,Community.class);
		UserProxy owner =  userClient.getUserByUserId(uid);
		if (owner == null) {
			throw new RuntimeException("User not found!");
		}
		Community community = new Community();
		Community newcommunity=null;
		if(owner!=null) {
			Integer Ownerid = owner.getId();

			community.setOwnerId(Ownerid);
			 newcommunity = helper.convert(communityProxy, Community.class);
			newcommunity.setCreatedAt(LocalDateTime.now());
			newcommunity.setIsActive(true);
			communityRepo.save(newcommunity);
		}
		
		return helper.convert(newcommunity, CommunityProxy.class);
	}








	@Override
	public List<CommunityProxy> getAllCommunity() {

		return helper.convertList(communityRepo.findAll(),CommunityProxy.class);
	}

	@Override
	public CommunityProxy getCommunityByName(String title) {

	return 	helper.convert(communityRepo.findByTitle(title),CommunityProxy.class);
	}

	@Override
	public CommunityProxy getCommunityById(Integer communityId) {
		return helper.convert(communityRepo.findById(communityId),CommunityProxy.class);
	}
	@Transactional
	@Override
	public String UpdateCommunity(Integer id, CommunityProxy communityProxy) {

		Optional<Community> community=communityRepo.findById(id);
		System.out.println("This is community------>"+community);
		System.out.println(community.isPresent());
		if(community.isPresent()){
			Community realcommunity =community.get();
			realcommunity.setTitle(communityProxy.getTitle());
			realcommunity.setDescription(communityProxy.getDescription());
			realcommunity.setOwnerId(communityProxy.getOwnerId());
			realcommunity.setIsActive(communityProxy.getIsActive());

			communityRepo.save(helper.convert(realcommunity,Community.class));

		}
		return "Community updated";
	}

	@Override
	public String DeleteCommunity(Integer id) {
		communityRepo.deleteById(id);
		return "Community is Deleted Sucessfully";
	}

	@Override
	public List<CommunityProxy> getCommunityByUserId(Integer id) {
		return helper.convertList(communityRepo.findByOwnerId(id),CommunityProxy.class);
	}

}
