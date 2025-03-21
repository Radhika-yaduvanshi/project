package com.serviceCommunity.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityContent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contentId;
//	private Integer communityId;
	private Integer uid;
	private String content;

	@ElementCollection
	@CollectionTable(name = "community_images", joinColumns = @JoinColumn(name = "content_id"))
	@Column(name = "image_url")
	private List<String> images;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;

	

}






