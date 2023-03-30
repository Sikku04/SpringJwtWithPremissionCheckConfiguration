package com.mixi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.mixi.model.UserInfo;
import com.mixi.repository.UserInfoRepository;
import com.mixi.service.UserinfoService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 1800)
public class AdminController {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	UserinfoService userInfoService;

	@PostMapping("/addSubAdmin")
	public ResponseEntity<?> addSubAdmin(@ModelAttribute UserInfo userInfo, HttpServletRequest request) {
		System.err.println(":::::UserInfoController:::addSubAdmin::::");
		ResultDTO<?> responsePacket = null;
		try {
			if (userInfo != null) {
				if (userInfoRepository.existsByEmail(userInfo.getEmail())
						|| userInfoRepository.existsByUserName(userInfo.getEmail())) {
					responsePacket = new ResultDTO<>(false, "UserName is already Exist");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				} else {
					if (userInfo.getPassword().equalsIgnoreCase(userInfo.getConfirmPassword())) {

						userInfoService.addSubAdmin(userInfo);

						responsePacket = new ResultDTO<>(true, "SubAdmin added Successfully");
						return new ResponseEntity<>(responsePacket, HttpStatus.OK);
					} else {
						responsePacket = new ResultDTO<>(false, "Password and ConfirmPassword Must be Same");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					}
				}
			} else {
				responsePacket = new ResultDTO<>(false, "User Data is Null");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/userList")
	public ResponseEntity<?> getUserList() {
		ResultDTO<?> responsePacket = null;
		try {
			List<UserInfo> userList = userInfoRepository.findAll();
			if (userList.isEmpty()) {
				responsePacket = new ResultDTO<>(true, "List is Empty");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(true, userList, "UserList Fetch Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/userListById/{id}")
	public ResponseEntity<?> getUserListById(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<UserInfo> data = userInfoRepository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a valid Id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				responsePacket = new ResultDTO<>(true, data, "UserList Fetch Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateSubAdmin/{id}")
	public ResponseEntity<?> updateSubAdmin(@PathVariable("id") Long id, @ModelAttribute UserInfo userInfo) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<UserInfo> data = userInfoRepository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a valid Id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				userInfoService.update(data, userInfo);
				responsePacket = new ResultDTO<>(true, "UserList Update Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/deleteSubAdminById/{id}")
	public ResponseEntity<?> deleteSubAdminById(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<UserInfo> data = userInfoRepository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a valid Id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				userInfoRepository.deleteById(id);
				responsePacket = new ResultDTO<>(true, "UserList Deleted Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateUserStatus/{id}")
	public ResponseEntity<?> updateUserStatus(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<UserInfo> data = userInfoRepository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a valid Id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				userInfoService.updateStatus(data);
				responsePacket = new ResultDTO<>(true, "Status Updated Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/GetUserProfileById/{id}")
	public ResponseEntity<?> GetUserProfileById(@PathVariable("id") Long id) {
		ResultDTO<?> responsePacket = null;
		try {
			Optional<UserInfo> data = userInfoRepository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "You are unauthorized?");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				responsePacket = new ResultDTO<>(true, data, "Profile  fetched successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

}
