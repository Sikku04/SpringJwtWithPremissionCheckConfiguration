package com.mixi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class MainBanner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "banner_name")
	private String bannerName;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	private String description;
	
	private String bannerVideo;
	private String joinNowUrl;
	
	private String videotitle;
	private String readMore;
	private Long languageId;
	
	private int position;
	private String createdAt;
	private String updatedAt;
	
	@Column(nullable=false,length =64)
	private String photos;
	
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}
	
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	

	public String getBannerVideo() {
		return bannerVideo;
	}

	public void setBannerVideo(String bannerVideo) {
		this.bannerVideo = bannerVideo;
	}

	public String getJoinNowUrl() {
		return joinNowUrl;
	}

	public void setJoinNowUrl(String joinNowUrl) {
		this.joinNowUrl = joinNowUrl;
	}
	

	public String getVideotitle() {
		return videotitle;
	}

	public void setVideotitle(String videotitle) {
		this.videotitle = videotitle;
	}

	public String getReadMore() {
		return readMore;
	}

	public void setReadMore(String readMore) {
		this.readMore = readMore;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
         
        return + id + "/" + photos;
    }

	@Override
	public String toString() {
		return "MainBanner [id=" + id + ", bannerName=" + bannerName + ", imageUrl=" + imageUrl + ", description="
				+ description + ", bannerVideo=" + bannerVideo + ", joinNowUrl=" + joinNowUrl + ", videotitle="
				+ videotitle + ", readMore=" + readMore + ", languageId=" + languageId + ", position=" + position
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", photos=" + photos + ", status=" + status
				+ "]";
	}

	

	
}
