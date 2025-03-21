package com.serviceCommunity.proxy;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityProxy {
	private Integer communityId;
	private String title;
	private String description;
	private Integer ownerId;//user id
	private Boolean isActive;
	private LocalDateTime createdAt;

}
