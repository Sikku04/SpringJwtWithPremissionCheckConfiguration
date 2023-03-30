package com.mixi.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mixi.bean.ChangePassword;
import com.mixi.bean.LoginRequest;
import com.mixi.bean.LoginResponse;
import com.mixi.bean.ResultDTO;
import com.mixi.configuration.jwt.JwtUtil;
import com.mixi.model.GlobalImage;
import com.mixi.model.UserInfo;
import com.mixi.repository.GlobalImageRepository;
import com.mixi.repository.UserInfoRepository;
import com.mixi.service.UserInfoDetailsImp;
import com.mixi.service.UserinfoService;
import com.mixi.utils.BeanValidator;
import com.mixi.utils.Constants;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 1800)
public class AuthController {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	UserinfoService userInfoService;

	@Autowired
	private JavaMailSender javaEmail;

	@Autowired
	private GlobalImageRepository globalImageRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BeanValidator beanValidator;

	@PostMapping("/signupUser")
	public ResponseEntity<?> addSubAdmin(@ModelAttribute UserInfo userInfo, HttpServletRequest request) {
		System.err.println(":::::UserInfoController:::addSubAdmin::::");
		ResultDTO<?> responsePacket = null;
		try {
			if (userInfo != null) {
				ArrayList<String> errorList = beanValidator.userSignupValidate(userInfo);
				if (!errorList.isEmpty()) {
					responsePacket = new ResultDTO<>(false, errorList, Constants.INVALID_DATA);
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				} else {
					if (userInfoRepository.existsByEmail(userInfo.getEmail())
							|| userInfoRepository.existsByUserName(userInfo.getEmail())) {
						responsePacket = new ResultDTO<>(false, "UserName is already Exist");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					} else {
						if (userInfo.getPassword().equalsIgnoreCase(userInfo.getConfirmPassword())) {

							userInfoService.addSubAdmin(userInfo);
							String type = "Registration";
							String link = Constants.getSiteURL(request) + "/auth/accountverify/" + userInfo.getEmail();
							sendEmail(userInfo.getEmail(), type, link, userInfo.getFirstName());
							responsePacket = new ResultDTO<>(true, "SubAdmin added Successfully");
							return new ResponseEntity<>(responsePacket, HttpStatus.OK);
						} else {
							responsePacket = new ResultDTO<>(false, "Password and ConfirmPassword Must be Same");
							return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
						}
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

	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@ModelAttribute LoginRequest loginRequest) {

		ResultDTO<?> responsePacket = null;
		try {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserInfoDetailsImp userDetails = (UserInfoDetailsImp) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(authentication);

			UserInfo user = userInfoRepository.findByEmail(loginRequest.getUserName());
			if (user.isEmailVerify()) {

				System.err.println("::::::::jwt::::::" + jwt);

				LoginResponse loginResponse = new LoginResponse(userDetails.getId(), userDetails.getUsername(),
						user.getFirstName(), userDetails.getEmail(), jwt);

				user.setLoginStatus(true);
				user.setLoginTime(Constants.getDateAndTime());
				userInfoRepository.save(user);
				responsePacket = new ResultDTO<>(true, loginResponse,
						"Dear " + user.getFirstName() + ", You are login successful!!");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);

			} else {
				responsePacket = new ResultDTO<>(false, "Your Account Is Not Verify");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/accountverify/{email}")
	public ResponseEntity<?> accountverify(@PathVariable("email") String email) {
		ResultDTO<?> responsePacket = null;
		try {
			UserInfo data = userInfoRepository.findByEmail(email);
			if (data != null) {
				userInfoService.verify(data);
				responsePacket = new ResultDTO<>(true, "Your Account is Verified");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid email Address");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/forgotPasssword/{email}")
	public ResponseEntity<?> forgotPasssword(@PathVariable("email") String email) {
		ResultDTO<?> responsePacket = null;
		try {
			UserInfo user = userInfoRepository.findByEmail(email);
			if (user != null) {
				String otp = userInfoService.sendOtp(user);
				String type = "ForgotPasssword";
				sendEmail(email, type, otp, user.getFirstName());
				responsePacket = new ResultDTO<>(true, "Otp Send on your Email");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid email Address");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/adminChangePassword")
	public ResponseEntity<?> adminChangePassword(@ModelAttribute ChangePassword changePassword) {
		System.err.println(":::::::::adminChangePassword::::");
		ResultDTO<?> responsePacket = null;
		try {
			UserInfo user = userInfoRepository.findById(changePassword.getUserId()).orElse(null);

			if (user != null) {
				if (user.getStatus()) {

					if (changePassword.getNewPassword().equalsIgnoreCase(changePassword.getConfimPassword())) {
						userInfoService.changePassword(user, changePassword.getNewPassword());

						responsePacket = new ResultDTO<>(true, "Your Password is changed Successfully");
						String type = "ChangePassword";
						sendEmail(user.getEmail(), type, null, user.getFirstName());
						return new ResponseEntity<>(responsePacket, HttpStatus.OK);
					} else {
						responsePacket = new ResultDTO<>(false, "Password and confirmPassword Must be Same");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					}
				} else {
					responsePacket = new ResultDTO<>(false, "Your Account is Not Verified");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				}
			} else {
				responsePacket = new ResultDTO<>(false, "Please Provide a Valid id");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/uploadImageToEditor")
	public ResponseEntity<?> uploadImageToEditor(@ModelAttribute GlobalImage globalImage,
			@RequestParam("file") MultipartFile file) {
		ResultDTO<?> responsePacket = null;
		try {
			if (file.isEmpty()) {
				responsePacket = new ResultDTO<>(false, "Please Select a File");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			} else {
				String path = Constants.IMAGE_EDITOR_LOCATION;
				String url = Constants.BASE_IP + Constants.IMAGE_EDITOR_LOCATION;
				String fName = "mixi" + Constants.getRandomNumber() + ".jpg";
				String fileName = Constants.saveMultipartFile(file, path, fName);
				globalImage.setCreatedAt(Constants.getDateAndTime());
				globalImage.setUpdatedAt(Constants.getDateAndTime());
				globalImage.setStatus(true);
				globalImage.setImageName(fileName);
				globalImage.setImgUrl(url);
				globalImageRepository.save(globalImage);
				responsePacket = new ResultDTO<>(true, "Image Upload Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}

		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}

	}

	public void sendEmail(String recipientEmail, String type, String link, String userName)
			throws UnsupportedEncodingException, MessagingException {
		try {
			MimeMessage message = javaEmail.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			if (type.equalsIgnoreCase("Account Verified")) {
				helper.setFrom("sikandar.saini@brsoftech.org", "Team Support");
				helper.setTo(recipientEmail);
				helper.setSubject(type);
				helper.setText("Please Verified your Account Click on This Link  " + link, true);
				javaEmail.send(message);
			}
			if (type.equalsIgnoreCase("Registration")) {
				helper.setFrom("sikandar.saini@brsoftech.org", "Team Support");
				helper.setTo(recipientEmail);
				helper.setSubject(type);
				String content = "<p>Hiii :" + userName + "<p><a href=\"" + link + "\">Verify Email</a></p>";
				helper.setText(content, true);
				javaEmail.send(message);
			}

			if (type.equalsIgnoreCase("ForgotPasssword")) {
				helper.setFrom("sikandar.saini@brsoftech.org", "Team Support");
				helper.setTo(recipientEmail);
				helper.setSubject(type);
				String otp = link;
				String content = "<p> Dear " + userName + "</p>" + " Your one time PIN is: " + otp
						+ ", and is valid for 10 minutes";
				helper.setText(content, true);
				javaEmail.send(message);
			}

			if (type.equalsIgnoreCase("ChangePassword")) {
				helper.setFrom("sikandar.saini@brsoftech.org", "Team Support");
				helper.setTo(recipientEmail);
				helper.setSubject(type);
				String content = "<p> Dear " + userName + "Your password is changed Successfully";
				helper.setText(content, true);
				javaEmail.send(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
