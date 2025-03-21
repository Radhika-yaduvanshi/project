package com.serviceCommunity.serviceImpl;


import com.serviceCommunity.entity.Community;
import com.serviceCommunity.entity.CommunityContent;
import com.serviceCommunity.proxy.CommunityContentProxy;
import com.serviceCommunity.repository.CommunityContentRepo;
import com.serviceCommunity.repository.CommunityMembersRepo;
import com.serviceCommunity.repository.CommunityRepo;
import com.serviceCommunity.service.CommunityContentService;
import com.serviceCommunity.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityContentImpl implements CommunityContentService {
    @Autowired
    private CommunityRepo communityRepo;
    @Autowired
    private CommunityContentRepo communityContentRepo;
    @Autowired
    private CommunityMembersRepo communityMembersRepo;
    @Autowired
    private Helper helper;



    @Override
    public CommunityContentProxy createContent(Integer communityId, Integer ownerId, CommunityContentProxy contentProxy) {

       Community community= communityRepo.findById(communityId).orElseThrow(()->new RuntimeException("Community Not Found"));
       if(!community.getOwnerId().equals(ownerId)){
           throw new RuntimeException("Only the community owner can create the content");
       }
       CommunityContent content=helper.convert(contentProxy,CommunityContent.class);
       content.setUid(ownerId);
       content.setContent(contentProxy.getContent());
       content.setCommunity(community);
       content.setImages(contentProxy.getImages());

        return  helper.convert(communityContentRepo.save(content),CommunityContentProxy.class);
    }


    //some work is pending in this 
    @Override
    public CommunityContentProxy updateContent(Integer contentId, Integer userId, String updatedContent) {
        CommunityContent content = communityContentRepo.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        content.setContent(updatedContent);

        content.setCreatedAt(LocalDateTime.now()); // Update timestamp

        CommunityContent updated = communityContentRepo.save(content);
        return  helper.convert(updated,CommunityContentProxy.class);

    }

    @Override
    public List<CommunityContentProxy> getCommunityContents(Integer communityId) {
        return List.of();
    }


//    @Override
//    public String addContent(Integer communityId, Integer userId, String content, List<String> imgurls) {
//        CommunityContent communityContent = new CommunityContent();
//        Community community=communityRepo.findById(communityId)	.orElseThrow(()->new RuntimeException("Community not found"));
//
//        boolean isOwner = community.getOwnerId().equals(userId);
//        boolean isMember = communityMembersRepo.existsByCommunityAndUid(community, userId);
//        CommunityContent newContent = new CommunityContent();
//        newContent.setUid(userId);
//        newContent.setContent(content);
//        newContent.setCreatedAt(LocalDateTime.now());
//        newContent.setCommunity(community);
////
////		List<CommunityImage> images = imageUrls.stream()
////				.map(url -> new CommunityImage(null, url, newContent))
////				.collect(Collectors.toList());
////		newContent.setImages(images);
//
//            communityContentRepo.save(newContent);
//
//
//
//        return  "Community post Updated";
//
//    }
}



