package com.mixi.utils;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixi.model.UserInfo;
import com.mixi.repository.RoleRepository;

@Component
public class BeanValidator {

	public Validator getValidation() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public String[] allRoleName() {

		return roleRepository.findAllByRoleName();
	}

	@PostConstruct
	public void updateRespData() {
		System.err.println("::: Update Resp Data :::");
		Constants.adminRespId();

	}

	public ArrayList<String> userSignupValidate(UserInfo reqData) {
		ArrayList<String> arrayList = new ArrayList<>();
		Set<ConstraintViolation<UserInfo>> ConstraintViolations = getValidation().validate(reqData);

		for (ConstraintViolation<UserInfo> ConstraintViolation : ConstraintViolations) {
			if (ConstraintViolation.getPropertyPath().toString().equals("firstName")) {
				arrayList.add(ConstraintViolation.getMessage());
			}
			if (ConstraintViolation.getPropertyPath().toString().equals("lastName")) {
				arrayList.add(ConstraintViolation.getMessage());
			}
			if (ConstraintViolation.getPropertyPath().toString().equals("email")) {
				arrayList.add(ConstraintViolation.getMessage());
			}
			if (ConstraintViolation.getPropertyPath().toString().equals("password")) {
				arrayList.add(ConstraintViolation.getMessage());
			}
			if (ConstraintViolation.getPropertyPath().toString().equals("confirmPassword")) {
				arrayList.add(ConstraintViolation.getMessage());
			}
		}

		return arrayList;
	}

}
