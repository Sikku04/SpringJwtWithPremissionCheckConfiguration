package com.mixi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class Constants {

	public static final String BASE_IP = "http://localhost:8085";
	public static final String IMAGE_EDITOR_LOCATION = "/home/brsoft/Main-eclipse-workspace/mixi/src/main/resources/static/image/editor_image/";
	public static final String INVALID_DATA = "invalidData";
	public static final String REQUEST_SUCCESS = "requestSuccess";
	public static final String BASE_DIR = "BASE_DIR";
	public static final String BANNER_LOCATION = "/home/brsoft/Main-eclipse-workspace/mixi/src/main/resources/static/image/editor_image/";

	public static String getDateAndTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
		Date date = new Date();
		String realDate = (formatter.format(date));
		return realDate;
	}

	public static String getRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);

	}

	public static String getSiteURL(HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
	}

	public static String saveMultipartFile(MultipartFile file, String path, String fileNameWithExtension)
			throws FileNotFoundException {
		File dir = new File(path + File.separator);
		if ((!dir.exists()) && (dir.mkdirs())) {
			System.err.println("Directory Created Successfully ");
		}
		dir = new File(path + File.separator + fileNameWithExtension);
		System.err.println(":::::ABSOLUTE_PATH:::" + dir.getAbsolutePath());
		FileOutputStream fileOutputStream = new FileOutputStream(dir);
		try {
			fileOutputStream.write(file.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileNameWithExtension;
	}

	public static HashMap<String, Long> adminResp = new HashMap<>();

	public static void adminRespId() {

		// Banner Management Module
		adminResp.put("addMainBanner", 100L);
	}

}
