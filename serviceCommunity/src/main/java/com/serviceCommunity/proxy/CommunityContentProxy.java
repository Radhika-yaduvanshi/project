package com.serviceCommunity.proxy;

import java.time.LocalDateTime;
import java.util.List;

import com.serviceCommunity.entity.Community;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityContentProxy {
	private Integer contentId;
	private Integer uid;
	private String content;


	private List<String> images;

	private LocalDateTime createdAt;

	private Community community;
}
