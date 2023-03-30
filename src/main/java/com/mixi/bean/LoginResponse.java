package com.mixi.bean;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String username;
	private String name;
	private String email;
	private String profileImageUrl;
	private String accessToken;
	private String type = "Bearer";

	public LoginResponse(Long id, String username, String name, String email, String accessToken) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.accessToken = accessToken;
	}

	public Long getId() {
		return id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}
