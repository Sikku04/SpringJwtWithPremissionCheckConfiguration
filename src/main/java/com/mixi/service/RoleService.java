package com.mixi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixi.model.Role;
import com.mixi.repository.RoleRepository;
import com.mixi.utils.Constants;

@Service
public class RoleService {

	@Autowired
	RoleRepository repository;

	public void addRole(Role role) {
		role.setCreatedAt(Constants.getDateAndTime());
		role.setUpdatedAt(Constants.getDateAndTime());
		role.setActive(true);
		repository.save(role);

	}

	public void update(Optional<Role> data, Role role) {
		Role existData = data.get();
		existData.setUpdatedAt(Constants.getDateAndTime());
		existData.setRoleName(role.getRoleName());
		existData.setResponsibility(role.getResponsibility());
		existData.setActive(true);
		repository.save(existData);

	}

	public void updateStatus(Optional<Role> data) {
		Role existData = data.get();
		if (existData.isActive()) {
			existData.setActive(false);
		} else {
			existData.setActive(true);
		}
		repository.save(existData);

	}

	public void saveRolesNResponsibilities(Role data, Role role) {

		Optional<Role> existData = repository.findById(data.getId());
		Role roledata = existData.get();

		if (existData.isPresent()) {
			roledata.setRoleName(role.getRoleName());
			roledata.setResponsibility(role.getResponsibility());
			roledata.setUpdatedAt(Constants.getDateAndTime());
			repository.save(roledata);
		}
	}

}
