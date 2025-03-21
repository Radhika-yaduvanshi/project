package com.serviceCommunity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.serviceCommunity.proxy.CommunityContentProxy;
import com.serviceCommunity.proxy.CommunityProxy;
import com.serviceCommunity.service.CommunityService;

@RestController
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@PostMapping("/cratecommunity/{id}")
	public CommunityProxy createCommunity(@RequestBody CommunityProxy communityProxy,@PathVariable("id") Integer id) {
		return communityService.createCommunity(communityProxy,id);
	}
	
//	@PostMapping("/sendrequest/{communityId}/{userId}")
//	public String requestToJoin(@PathVariable("communityId") Integer communityId,@PathVariable("userId") Integer userId) {
//		return communityService.requestToJoin(communityId, userId);
//	}
//
//	@PostMapping("/approverequest/{communityId}/{userId}")
//	public String approveJoinRequest(@PathVariable("communityId") Integer communityId,@PathVariable("userId") Integer userId) {
//		return communityService.approveJoinRequest(communityId, userId);
//	}
	
//	@GetMapping("/getcommunitycontent/{communityId}")
//	public List<CommunityContentProxy> getCommunityContent(@PathVariable("communityId") Integer communityId){
//		return communityService.getCommunityContent(communityId);
//	}



	//crud of community
	@GetMapping("/getallcommunity")
	public List<CommunityProxy> getAllCommunity() {

		return communityService.getAllCommunity();
	}

	@GetMapping("/getbytitle/{title}")
	public CommunityProxy getCommunityByName(@PathVariable("title") String title) {

		return 	communityService.getCommunityByName(title);
	}

	@GetMapping("/getbyid/{communityId}")
	public CommunityProxy getCommunityById(@PathVariable("communityId") Integer communityId) {
		return communityService.getCommunityById(communityId);
	}

	@PutMapping("/updatecommunity/{id}")
	String UpdateCommunity(@PathVariable("id")Integer id,@RequestBody CommunityProxy communityProxy){
		return  communityService.UpdateCommunity(id,communityProxy);
	}
	@DeleteMapping("/deletecommunity/{id}")
	String DeleteCommunity(@PathVariable("id")Integer id){
		return  communityService.DeleteCommunity(id);
	}

	@GetMapping("/getcommunitybyuserid/{id}")
	public List<CommunityProxy> getCommunityByUserId(@PathVariable("id") Integer id) {

		return communityService.getCommunityByUserId(id);
	}


}
