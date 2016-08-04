package com.simple.gcode.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Utilities {
	public static String formatDate(Date d, String format) {
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.format(d);
	}

	public static Date getDateFrom(String strDate, String format) throws Exception {
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.parse(strDate);
	}

	public static List<Date> getDates(Date dateBegin, Date dateEnd) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(dateBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dateBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dateEnd);
		// 测试此日期是否在指定日期之后
		while (dateEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DATE, 1);
			dates.add(calBegin.getTime());
		}
		return dates;
	}

	public static String getPaginateClauseForMysql(int page, int pageSize) {
		int start = (page - 1) * pageSize;
		return " limit " + start + "," + pageSize;
	}

	public static String getValue(String name, String str) {
		String v = "";
		int i = str.indexOf(name);
		if (i > 0) {
			i = str.indexOf(":", i);
			if (i > 0) {
				int e = str.indexOf(",", i);
				int e1 = str.indexOf("\"", i);
				if (e1 < e) {
					int e2 = str.indexOf("\"", e1 + 1);
					if (e2 > e1)
						e = e2;
				}
				if (e < 0)
					e = str.indexOf("}", i);
				v = str.substring(i + 1, e);
			}
		}
		v = v.replaceAll("\\\"", "");
		return v;
	}

	public static InputStream getInputStreamFromFile(String filename) throws Exception {
		File f = new File(filename);
		if (f.exists())
			return new FileInputStream(filename);
		else {
			return Utilities.class.getClassLoader().getResourceAsStream(filename);
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 首字母转为大写
	 * 
	 * @author ldm
	 * @Date 2016年8月4日
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}
	/**
	 * 首字母转为小写
	 * 
	 * @author ldm
	 * @Date 2016年8月4日
	 * @param name
	 * @return
	 */
	public static String captureVarName(String name) {
		char[] cs = name.toCharArray();
		cs[0] += 32;
		return String.valueOf(cs);

	}
}
