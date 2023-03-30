package com.mixi.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import com.mixi.repository.RoleRepository;

@Configuration
public class AuthorityCheckConfiguration {

	@Autowired
	private RoleRepository roleRepository;

	public List<String> adminCheckAuthority(Long res, Collection<? extends GrantedAuthority> userAuth) {

		List<String> emptyList = new ArrayList<>();
		List<String> userRoles = new ArrayList<>();

		String adminRole = "";
		String roleName = "";

		for (GrantedAuthority urole : userAuth) {

			System.err.println(":::::urole.getAuthority():::" + urole.getAuthority());
			adminRole = urole.getAuthority();
			roleName = urole.getAuthority();
			userRoles.add(urole.getAuthority());

		}
		if (adminRole.equalsIgnoreCase("ADMIN")) {
			System.out.println(":::ROLE::: " + adminRole);

			return userRoles;

		} else {

			long roleId = roleRepository.findRoleIdByRoleName(roleName);
			System.out.println(":::::: roleId :::::   " + roleId + "::::  roleName ::::: " + roleName);
			// :::::: roleId ::::: 3:::: roleName ::::: SUB_ADMIN

			List<Long> respList = roleRepository.findRespnsbilitiesByRoleId(roleId);

			System.out.println(
					"Resp is ::: - " + res + " & SubAdmin Responsibilities List ::: " + Arrays.asList(respList));
			// Resp is ::: - 100 & SubAdmin Responsibilities List ::: [[1]]
			if (respList.contains(res))
				return userRoles;
			else
				return emptyList;

		}

	}

}
