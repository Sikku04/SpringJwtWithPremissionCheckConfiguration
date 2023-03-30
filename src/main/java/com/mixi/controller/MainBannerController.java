package com.mixi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mixi.bean.ResultDTO;
import com.mixi.configuration.AuthorityCheckConfiguration;
import com.mixi.model.MainBanner;
import com.mixi.repository.MainBannerRepository;
import com.mixi.service.MainBannerService;
import com.mixi.utils.Constants;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainBannerController {

	@Autowired
	private MainBannerService mainBannerService;

	@Autowired
	private MainBannerRepository mainBannerRepository;

	@Autowired
	private AuthorityCheckConfiguration permissionCheck;

	@PostMapping("/addMainBanner")
	public ResponseEntity<?> addMainBanner(@ModelAttribute MainBanner mainBanner,
			@RequestParam("file") MultipartFile file, Authentication auth) {
		ResultDTO<?> responsePacket = null;
		try {

			List<String> status = permissionCheck.adminCheckAuthority(Constants.adminResp.get("addMainBanner"),
					auth.getAuthorities());
			System.err.println(":::::status::::" + status);
			if (!status.isEmpty()) {
				if (mainBanner != null) {
					if (mainBannerRepository.existsByBannerName(mainBanner.getBannerName())) {
						responsePacket = new ResultDTO<>(false, "MainBanner is already Exist");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					}
					if (file.isEmpty()) {
						responsePacket = new ResultDTO<>(false, "Please Select a File");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					} else {
						mainBannerService.add(mainBanner, file);
						responsePacket = new ResultDTO<>(true, "MainBanner Is added Successfully");
						return new ResponseEntity<>(responsePacket, HttpStatus.OK);
					}
				} else {
					responsePacket = new ResultDTO<>(false, "Banner Data is Null");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				}
			} else {
				responsePacket = new ResultDTO<>(false, null, "MainBanner Add Permission Is Denied!");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

}
