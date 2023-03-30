package com.mixi.service;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mixi.model.MainBanner;
import com.mixi.repository.MainBannerRepository;
import com.mixi.utils.Constants;

@Service
public class MainBannerService {

	@Autowired
	MainBannerRepository mainBannerRepository;

	public void add(MainBanner mainBanner, MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String path = Constants.BANNER_LOCATION;
			String fname = "Banner_" + Constants.getRandomNumber() + ".jpg";
			String fileName = Constants.saveMultipartFile(file, path, fname);
			System.err.println("fileanae  ::::::" + fileName);
			String url = Constants.BASE_IP + Constants.BANNER_LOCATION;
			mainBanner.setImageUrl(url);
			mainBanner.setPhotos(fileName);
			mainBanner.setStatus(true);
			mainBanner.setCreatedAt(Constants.getDateAndTime());
			mainBanner.setUpdatedAt(Constants.getDateAndTime());
			
		}

		mainBannerRepository.save(mainBanner);

	}

}
