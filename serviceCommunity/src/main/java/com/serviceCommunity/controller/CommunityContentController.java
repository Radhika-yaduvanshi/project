package com.serviceCommunity.controller;


import com.serviceCommunity.proxy.CommunityContentProxy;
import com.serviceCommunity.serviceImpl.CommunityContentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community/content")
public class CommunityContentController {

    @Autowired
    private CommunityContentImpl contentService;

    @PostMapping("/create/{communityId}/owner/{ownerId}")
    public CommunityContentProxy createContent(
            @PathVariable Integer communityId,
            @PathVariable Integer ownerId,
            @RequestBody CommunityContentProxy contentProxy) {
        return contentService.createContent(communityId, ownerId, contentProxy);
    }
}
