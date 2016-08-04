package com.simple.gcode.utils;


public class ConfigUtils {
	public static String read(String key) {
		try {
			return PropertiesUtils.init("config").read(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
