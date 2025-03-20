package com.javaweb.utils;

import java.security.PublicKey;

public class NumberUtil {
	public static boolean isNumber(String s) {
		try {
			Long number = Long.parseLong(s); 
		}catch (NumberFormatException ex) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
