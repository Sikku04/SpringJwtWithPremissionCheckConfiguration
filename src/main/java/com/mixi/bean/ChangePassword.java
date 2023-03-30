package com.mixi.bean;

public class ChangePassword {

	private Long userId;
	private String otp;
	private String oldPassword;
	private String newPassword;
	private String confimPassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfimPassword() {
		return confimPassword;
	}

	public void setConfimPassword(String confimPassword) {
		this.confimPassword = confimPassword;
	}

	@Override
	public String toString() {
		return "ChangePassword [userId=" + userId + ", otp=" + otp + ", oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + ", confimPassword=" + confimPassword + "]";
	}

}
