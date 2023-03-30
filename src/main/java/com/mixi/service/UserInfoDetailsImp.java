package com.mixi.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mixi.model.UserInfo;

public class UserInfoDetailsImp implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String password;
	private String email;
	private boolean loginStatus;
	private Collection<? extends GrantedAuthority> getAuthorities;

	public UserInfoDetailsImp(Long id, String userName, String password, String email, boolean loginStatus,
			Collection<? extends GrantedAuthority> getAuthorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.loginStatus = loginStatus;
		this.getAuthorities = getAuthorities;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGetAuthorities(Collection<? extends GrantedAuthority> getAuthorities) {
		this.getAuthorities = getAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	public static UserInfoDetailsImp buildUserWithAuth(UserInfo user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
		return new UserInfoDetailsImp(user.getId(), user.getUserName(), user.getPassword(), user.getEmail(),
				user.isLoginStatus(), authorities);
	}

}
