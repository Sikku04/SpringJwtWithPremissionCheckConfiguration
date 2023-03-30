package com.mixi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mixi.bean.ResultDTO;
import com.mixi.model.Role;
import com.mixi.repository.RoleRepository;
import com.mixi.service.RoleService;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", maxAge = 1800)
public class RoleController {

	@Autowired
	RoleRepository repository;

	@Autowired
	RoleService roleService;

	@PostMapping("/addRole")
	public ResponseEntity<?> addRole(@ModelAttribute Role role) {
		System.err.println("::: AdminController.addRole :::");
		ResultDTO<?> responsePacket = null;
		try {
			if (role != null) {
				if (role.getRoleName().isEmpty()) {
					responsePacket = new ResultDTO<>(false, null, "Please Provide Role Name");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				} else {
					Role data = repository.findByRoleName(role.getRoleName());
					if (data != null) {
						responsePacket = new ResultDTO<>(false, "RoleName is already Exist");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					} else {
						roleService.addRole(role);
						responsePacket = new ResultDTO<>(true, "Role Added Successfully");
						return new ResponseEntity<>(responsePacket, HttpStatus.OK);
					}
				}
			} else {
				responsePacket = new ResultDTO<>(false, null, "Role Data is null");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, null, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/getRoleList")
	public ResponseEntity<?> getRoleList() {
		System.err.println("::: AdminController.getRoleList :::");
		ResultDTO<?> responsePacket = null;
		try {
			List<Role> roleList = repository.findAll();
			if (!roleList.isEmpty()) {
				responsePacket = new ResultDTO<>(true, roleList, "RoleListFetch successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(true, null, "RoleList is Empty");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/roleById/{id}")
	public ResponseEntity<?> getRoleById(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<Role> data = repository.findById(id);
			if (data.isPresent()) {
				responsePacket = new ResultDTO<>(true, data, "Role Details Fetch Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateRoleById/{id}")
	public ResponseEntity<?> updateRoleById(@PathVariable("id") Long id, @ModelAttribute Role role) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<Role> data = repository.findById(id);
			if (data.isPresent()) {
				roleService.update(data, role);
				responsePacket = new ResultDTO<>(true, "Role Updated Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);

			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/deleteRoleById/{id}")
	public ResponseEntity<?> deleteRoleById(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<Role> data = repository.findById(id);
			if (data.isPresent()) {
				repository.deleteById(id);
				responsePacket = new ResultDTO<>(true, "Role Details is deleted Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);

			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateRoleStatusById/{id}")
	public ResponseEntity<?> updateRoleStatusById(@PathVariable("id") Long id) {
		System.err.println("::: AdminController.updateRoleStatusById :::");
		ResultDTO<?> responsePacket = null;
		try {
			Optional<Role> data = repository.findById(id);
			if (data.isPresent()) {
				roleService.updateStatus(data);
				responsePacket = new ResultDTO<>(true, "Role Update Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	@PostMapping("/setRoleResponsibility")
	public ResponseEntity<?> setRolesAndResponsibility(@ModelAttribute Role role) {
		ResultDTO<?> responsePacket = null;
		Role data = repository.findByRoleName(role.getRoleName());
		try {
			if (data != null) {
				roleService.saveRolesNResponsibilities(data, role);
				responsePacket = new ResultDTO<>(true, "Responsibilities assign to Role");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(true, null, "Record not fount !!");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

}
