package com.mixi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String args[]) {
		getDateAndTime();
	}

	public static String getDateAndTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
		Date date = new Date();
		String realDate = (formatter.format(date));
		System.err.println("::CurrentDateTime::::" + realDate);
		return realDate;
	}
}
