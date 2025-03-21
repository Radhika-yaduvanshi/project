package com.serviceCommunity.repository;

import com.serviceCommunity.proxy.CommunityProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serviceCommunity.entity.Community;

import java.util.List;

@Repository
public interface CommunityRepo extends JpaRepository<Community, Integer>{

    Community findByTitle(String title);
    List<Community> findByOwnerId(Integer id);
}
