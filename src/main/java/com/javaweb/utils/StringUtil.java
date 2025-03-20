package com.javaweb.utils;

public class StringUtil {
	public static boolean notNull(String s) {
		if(s != null && !s.equals("")) {
			return true;
		}
		return false;
	}
}
