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
import com.mixi.model.CMSResponsibility;
import com.mixi.repository.CMSRepository;
import com.mixi.service.CmsService;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", maxAge = 1800)
public class CMSResponsibilityController {

	@Autowired
	CMSRepository repository;

	@Autowired
	CmsService service;

	@PostMapping("/addResponsibilty")
	public ResponseEntity<?> addResponsibilty(@ModelAttribute CMSResponsibility responsibility) {

		ResultDTO<?> responsePacket = null;
		try {
			if (responsibility != null) {
				if (repository.existsByRespName(responsibility.getRespName())) {
					responsePacket = new ResultDTO<>(false, "Responsibility is already exist");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				} else {
					service.addResponsibility(responsibility);
					responsePacket = new ResultDTO<>(true, "Responsibility added Successfully");
					return new ResponseEntity<>(responsePacket, HttpStatus.OK);
				}
			} else {
				responsePacket = new ResultDTO<>(false, "Null Value");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/responsibilityById/{id}")
	public ResponseEntity<?> responsibilityById(@PathVariable("id") Long id) {
		System.err.println("::CMSResponsibilityController:::responsibilityList:");
		ResultDTO<?> responsePacket = null;
		try {
			Optional<CMSResponsibility> data = repository.findById(id);

			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				responsePacket = new ResultDTO<>(true, data, "Responsibility Details Fetch Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/responsibilityList")
	public ResponseEntity<?> getResponsibilityList() {
		System.err.println("::CMSResponsibilityController:::responsibilityList:");
		ResultDTO<?> responsePacket = null;
		try {
			List<CMSResponsibility> resList = repository.findAll();
			System.err.println("::CMSResponsibilityController:::resList:::" + resList.size());
			if (resList.isEmpty()) {
				responsePacket = new ResultDTO<>(true, "Responsibility List is Empty");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(true, resList, "Responsibility List Fetch Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * @PostMapping("/deleteResponsibilityById/{id}") public ResponseEntity<?>
	 * deleteResponsibilityById(@PathVariable("id") Long id) { System.err.println(
	 * "::CMSResponsibilityController:::DeleteResponsibilityById:"); ResultDTO<?>
	 * responsePacket = null; try { Optional<CMSResponsibility> data =
	 * repository.findById(id);
	 * 
	 * if (!data.isPresent()) { responsePacket = new ResultDTO<>(false,
	 * "Please Provide a Valid id"); return new ResponseEntity<>(responsePacket,
	 * HttpStatus.BAD_REQUEST); } else { repository.deleteById(id); responsePacket =
	 * new ResultDTO<>(true, data, "Responsibility Delete Successfully"); return new
	 * ResponseEntity<>(responsePacket, HttpStatus.OK); }
	 * 
	 * } catch (Exception e) { responsePacket = new ResultDTO<>(false,
	 * e.getMessage()); return new ResponseEntity<>(responsePacket,
	 * HttpStatus.BAD_REQUEST); }
	 * 
	 * }
	 */

	@PostMapping("/updateResponsibilityById/{id}")
	public ResponseEntity<?> deleteResponsibilityById(@PathVariable("id") Long id,
			@ModelAttribute CMSResponsibility cmsResponsibility) {
		System.err.println("::CMSResponsibilityController:::updateResponsibilityById:");
		ResultDTO<?> responsePacket = null;
		try {
			Optional<CMSResponsibility> data = repository.findById(id);
			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				service.Update(data, cmsResponsibility);
				responsePacket = new ResultDTO<>(true, "Responsibility Update Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/updateResponsibilityStatusById/{id}")
	public ResponseEntity<?> updateResponsibilityStatusById(@PathVariable("id") Long id) {

		ResultDTO<?> responsePacket = null;
		try {
			Optional<CMSResponsibility> data = repository.findById(id);

			if (!data.isPresent()) {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				service.UpdateStatus(data);
				responsePacket = new ResultDTO<>(true, "Status Update Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

}
