package com.mixi.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mixi.model.Role;
import com.mixi.model.UserInfo;
import com.mixi.repository.RoleRepository;
import com.mixi.repository.UserInfoRepository;
import com.mixi.utils.Constants;

@Service
public class UserinfoService implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo data = userInfoRepository.findByEmail(username);
		return UserInfoDetailsImp.buildUserWithAuth(data);
	}

	public void addSubAdmin(UserInfo userInfo) {
		userInfo.setCreatedAt(Constants.getDateAndTime());
		userInfo.setUpdatedAt(Constants.getDateAndTime());
		userInfo.setFirstName(userInfo.getFirstName());
		userInfo.setLastName(userInfo.getLastName());
		userInfo.setUserName(userInfo.getEmail());
		userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
		userInfo.setConfirmPassword(bCryptPasswordEncoder.encode(userInfo.getConfirmPassword()));
		userInfo.setEmail(userInfo.getEmail());
		userInfo.setStatus(true);

		Role userRole = roleRepository.findById(userInfo.getRole()).orElse(null);

		Set<Role> roleSet = new HashSet<>();
		roleSet.add(userRole);

		userInfo.setRoles(roleSet);
		userInfoRepository.save(userInfo);

	}

	public void update(Optional<UserInfo> data, UserInfo userInfo) {
		UserInfo existData = data.get();
		existData.setFirstName(userInfo.getFirstName());
		existData.setLastName(userInfo.getLastName());
		existData.setEmail(userInfo.getEmail());
		existData.setUserName(userInfo.getUserName());
		existData.setUpdatedAt(Constants.getDateAndTime());
		Role userRole = roleRepository.findById(userInfo.getRole()).orElse(null);
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(userRole);
		existData.setRoles(roleSet);
		userInfoRepository.save(existData);

	}

	public void updateStatus(Optional<UserInfo> data) {
		UserInfo existData = data.get();
		if (existData.getStatus()) {
			existData.setStatus(false);
		} else {
			existData.setStatus(true);
		}
		userInfoRepository.save(existData);

	}

	public void verify(UserInfo data) {
		UserInfo existData = userInfoRepository.findById(data.getId()).orElse(null);
		existData.setEmailVerify(true);
		existData.setUpdatedAt(Constants.getDateAndTime());
		userInfoRepository.save(existData);

	}

	public void changePassword(UserInfo user, String newPassword) {
		System.err.println("::::UserInfoService:::::changePassword::::");
		user.setPassword(newPassword);
		user.setConfirmPassword(newPassword);
		user.setUpdatedAt(Constants.getDateAndTime());
		userInfoRepository.save(user);

	}

	public String sendOtp(UserInfo user) {
		user.setOtp(Constants.getRandomNumber());
		user.setUpdatedAt(Constants.getDateAndTime());
		userInfoRepository.save(user);
		return user.getOtp();

	}

}
