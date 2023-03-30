package com.mixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixi.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	boolean existsByEmail(String email);

	boolean existsByUserName(String email);

	UserInfo findByEmail(String email);

}
