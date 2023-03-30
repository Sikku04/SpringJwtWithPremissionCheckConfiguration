package com.mixi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixi.model.CMSResponsibility;
import com.mixi.repository.CMSRepository;
import com.mixi.utils.Constants;

@Service
public class CmsService {

	@Autowired
	CMSRepository repository;

	public void addResponsibility(CMSResponsibility responsibility) {

		responsibility.setRespName(responsibility.getRespName());
		responsibility.setCreatedAt(Constants.getDateAndTime());
		responsibility.setUpdatedAt(Constants.getDateAndTime());
		responsibility.setActive(true);
		repository.save(responsibility);

	}

	public void Update(Optional<CMSResponsibility> data, CMSResponsibility cmsResponsibility) {
		CMSResponsibility existData = data.get();
		existData.setUpdatedAt(Constants.getDateAndTime());
		existData.setRespName(cmsResponsibility.getRespName());
		repository.save(existData);

	}

	public void UpdateStatus(Optional<CMSResponsibility> data) {
		CMSResponsibility existData = data.get();
		if (existData.isActive()) {
			existData.setActive(false);
		} else {
			existData.setActive(true);
		}
		repository.save(existData);

	}

}
