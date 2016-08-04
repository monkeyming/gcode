package com.simple.gcode.utils;
/**
 * 
 * @author ldm
 * @Date 2016年8月4日
 */
public class NameConverter {
	/**
	 * 数据库命名方式转换成java的命名方式
	 * 
	 * @param s
	 * @return
	 */
	public static String toJavaCase(String s) {
		if (s == null || s.trim().length() == 0)
			return s;
		// 去掉第一个下划线及其前面的字母
		//s = s.substring(s.indexOf("_") + 1);
		StringBuffer sb = new StringBuffer();
		String[] array = s.split("_");
		boolean firstTime = true;
		for (String e : array) {
			if (e.length() == 0)
				continue;
			else if (e.length() == 1)
				sb.append(!firstTime ? e.toUpperCase() : e);
			else
				sb.append(!firstTime ? (e.substring(0, 1).toUpperCase() + e
						.substring(1)) : e);
			firstTime = false;
		}
		return sb.toString();
	}

	/**
	 * Java命名方式转换成数据库的命名方式
	 * 
	 * @param s
	 * @return
	 */
	public static String toDbCase(String s) {
		if (s == null || s.trim().length() == 0)
			return s;
		char[] chars = s.toCharArray();
		boolean firstTime = true;
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (c >= 'A' && c <= 'Z') {
				char c1 = (char) (c + 32);
				sb.append(firstTime ? c1 : "_" + c1);
			} else
				sb.append(c);
			firstTime = false;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out
		// .println(toDbCase("theyBeganToHumWhenSeeingJacksonWalkIntoTheHall"));
		// System.out
		// .println(toJavaCase(toDbCase("theyBeganToHumWhenSeeingJacksonWalkIntoTheHall")));
		StringBuffer sb = new StringBuffer("sdsdfds1");
		sb.delete(sb.length() - 1, sb.length());
		System.out.println(NameConverter.toJavaCase("ww_ww"));
	}
}
