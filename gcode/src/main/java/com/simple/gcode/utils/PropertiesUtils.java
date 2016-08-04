package com.simple.gcode.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件加载辅助类
 * </p>
 * 用来直接加载 配置properties配置文件。
 * </p>
 * 没词读取数据都会从配置文件加载，修改配置文件不需要重新启动服务，用到时需要考虑配置文件的读取频率
 * 
 * @author ldm
 * @Date 2016年7月13日
 */
public class PropertiesUtils {
	// 限制读取文件的后缀
	private String suffix = ".properties";
	private Properties properties;

	private PropertiesUtils(String fileName) throws Exception {
		String filePath = fileName + suffix;
		InputStream is = Utilities.getInputStreamFromFile(filePath);
		
		if (is == null)
			throw new Exception(filePath + " read failed");
		
		properties = new Properties();
		properties.load(is);
	}

	/**
	 * 初始化工具类，并返回初始化后的类实例
	 * 
	 * @author ldm
	 * @Date 2016年7月13日
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static PropertiesUtils init(String fileName) throws Exception {
		return new PropertiesUtils(fileName);
	}

	/**
	 * 读取指定key的值</p>
	 * 读取不到的时候返回默认值<tt>null</tt>
	 * 
	 * @author ldm
	 * @Date 2016年7月13日
	 * @param key
	 * @return
	 */
	public String read(String key) {
		return properties.getProperty(key, null);
	}
}
