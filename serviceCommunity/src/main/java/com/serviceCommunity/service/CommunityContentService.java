package com.serviceCommunity.service;

import com.serviceCommunity.proxy.CommunityContentProxy;

import java.util.List;

public interface CommunityContentService {

//    String addContent(Integer communityId, Integer userId, String content, List<String > imgurls);

    CommunityContentProxy createContent(Integer communityId, Integer ownerId, CommunityContentProxy contentProxy);
    CommunityContentProxy updateContent(Integer contentId, Integer userId, String updatedContent);
    List<CommunityContentProxy> getCommunityContents(Integer communityId);

}
