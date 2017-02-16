package com.simple.gcode.freemarker;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.simple.gcode.utils.BaseConfig;
import com.simple.gcode.utils.ConfigUtils;
import com.simple.gcode.utils.EntityBean;

import freemarker.template.Template;

/**
 * 
 * FreemarkerService.java
 * 
 * @description 配置连接信息 以及数据库数据类型对应的java数据类型
 * @author ldm
 * @date 2016年12月18日
 */
public abstract class FreemarkerService {

	public static String url = ConfigUtils.read("dbType").equals("mysql") ? ConfigUtils.read("db.url")
			: ConfigUtils.read("db.sqlserver.url");

	public static String user = ConfigUtils.read("dbType").equals("mysql") ? ConfigUtils.read("db.username")
			: ConfigUtils.read("db.sqlserver.username");

	public static String password = ConfigUtils.read("dbType").equals("mysql") ? ConfigUtils.read("db.password")
			: ConfigUtils.read("db.sqlserver.password");

	public static String driver = ConfigUtils.read("dbType").equals("mysql") ? ConfigUtils.read("db.driver")
			: ConfigUtils.read("db.sqlserver.driver");

	public static void setEntityBean(DatabaseMetaData dbmd, ResultSetMetaData rsmd, EntityBean entityBean, String tb,
			int size, String[] colnames, String[] colTypes, String[] remarks) {
		try {
			ResultSet columnSet = dbmd.getColumns(null, "%", tb, "%"); // 获取一个表的所有的列
			int k = 0;
			while (columnSet.next()) {

				String columnComment = columnSet.getString("REMARKS") == null ? "" : columnSet.getString("REMARKS");
				remarks[k] = columnComment;
				k++;
			}
			ResultSet _ResultSet = dbmd.getPrimaryKeys(null, null, tb);
			if (_ResultSet.next()) {
				entityBean.setPrimaryKey(_ResultSet.getObject(4) + "");
			}
			for (int i = 0; i < size; i++) {

				colnames[i] = rsmd.getColumnName(i + 1);
				String jdbcType = rsmd.getColumnTypeName(i + 1);
				if (jdbcType.equalsIgnoreCase("int"))
					jdbcType = "INTEGER";
				if (jdbcType.equalsIgnoreCase("DATETIME"))
					jdbcType = "TIMESTAMP";
				colTypes[i] = jdbcType;
				if (colnames[i].equalsIgnoreCase(entityBean.getPrimaryKey())) {
					entityBean.setPrimaryKeyType(colTypes[i]);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 首字母转为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firsetLetterUpper(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public void createJavaFile(Map<String, Object> data, String ftl, EntityBean entityBean, BaseConfig baseConfig,
			FreeMarkerConfigurer freeMarkerConfigurer) {

		try {

			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftl);
			template.setEncoding("utf-8");
			String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
			String tmpDir = null;
			if (StringUtils.isNotBlank(baseConfig.getEntityPath())) {
				String packagePath = entityBean.getPackagePath();
				if (!packagePath.endsWith(".")) {
					packagePath += ".";
				}
				tmpDir = baseConfig.getEntityPath() + packagePath.replace(".", "/");
				File htmlFile = new File(tmpDir);
				if (!htmlFile.exists())
					htmlFile.mkdirs();

				htmlFile = new File(tmpDir + entityBean.getFileName() + baseConfig.getSuffix());
				FileUtils.write(htmlFile, htmlText, Charset.forName("utf-8"));
			}
			/*
			 * if (StringUtils.isNotBlank(baseConfig.getMapperXmlPath())) {
			 * tmpDir = baseConfig.getMapperXmlPath() + "/src/main/resources/" +
			 * entityBean.getPackagePath().replace(".", "/");
			 * baseConfig.setSuffix(".xml"); File htmlFile = new File(tmpDir);
			 * if (!htmlFile.exists()) htmlFile.mkdirs();
			 * 
			 * htmlFile = new File(tmpDir + entityBean.getFileName() +
			 * baseConfig.getSuffix()); FileUtils.write(htmlFile, htmlText); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将sql数据类型转为java数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	public static String sqlType2JavaType(String sqlType) {
		System.out.println("sqlType  = " + sqlType);
		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("integer") || sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("double") || sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return null;
	}

	public static String getPackage(String fixpackage) {
		fixpackage = fixpackage.toLowerCase();
		// category
		if (fixpackage.startsWith("category")) {
			fixpackage = "category";
		}
		if (fixpackage.startsWith("msg")) {
			fixpackage = "msg";
		}
		if (fixpackage.startsWith("org")) {
			fixpackage = "org";
		}

		if (fixpackage.startsWith("img")) {
			fixpackage = "img";
		}
		if (fixpackage.startsWith("img")) {
			fixpackage = "img";
		}
		if (fixpackage.startsWith("dict")) {
			fixpackage = "dict";
		}
		if (fixpackage.startsWith("system")) {
			fixpackage = "system";
		}
		if (fixpackage.startsWith("apply")) {
			fixpackage = "apply";
		}
		if (fixpackage.startsWith("authority")) {
			fixpackage = "authority";
		}
		if (fixpackage.startsWith("match")) {
			fixpackage = "match";
		}
		if (fixpackage.startsWith("school")) {
			fixpackage = "school";
		}
		if (fixpackage.startsWith("user")) {
			fixpackage = "user";
		}
		if (fixpackage.startsWith("tuan")) {
			fixpackage = "tuan";
		}
		if (fixpackage.startsWith("cms")) {
			fixpackage = "cms";
		}
		if (fixpackage.startsWith("feedback")) {
			fixpackage = "feedback";
		}
		if (fixpackage.startsWith("order")) {
			fixpackage = "order";
		}
		return fixpackage;
	}

}
