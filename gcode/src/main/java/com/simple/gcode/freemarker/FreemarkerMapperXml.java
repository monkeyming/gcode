package com.simple.gcode.freemarker;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.simple.gcode.utils.BaseConfig;
import com.simple.gcode.utils.ConfigUtils;
import com.simple.gcode.utils.EntityBean;
import com.simple.gcode.utils.EntityProperties;
import com.simple.gcode.utils.NameConverter;

/**
 * 生成mybatis的xml映射文件
 * 
 * @author ldm
 * @Date 2016年8月4日
 */
public class FreemarkerMapperXml extends FreemarkerService {
	public static void run(BaseConfig baseConfig, FreeMarkerConfigurer freeMarkerConfigurer) throws Exception {
		FreemarkerService freemarkerService = new FreemarkerMapperXml();
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, user, password);
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet resultSet = dbmd.getTables(null, null, null, null);
		List<String> listTb = new ArrayList<String>();
		// 设置要过滤的表
		String tableTag = ConfigUtils.read("table.tag");
		while (resultSet.next()) {
			String tableName = resultSet.getString(3);
			if (tableTag == null || tableTag.isEmpty() || tableTag.equals("*")) {

				listTb.add(tableName);
			} else if (tableName.contains(tableTag)) {
				listTb.add(tableName);
			}
		}

		String sql = "select * from ";
		PreparedStatement pstemt = null;
		for (String tb : listTb) {
			pstemt = connection.prepareStatement(sql + tb);
			ResultSetMetaData rsmd = pstemt.getMetaData();
			int size = rsmd.getColumnCount();
			String[] colnames = new String[size];
			String[] colTypes = new String[size];
			String[] remarks = new String[size];
			EntityBean entityBean = new EntityBean();
			setEntityBean(dbmd, rsmd, entityBean, tb, size, colnames, colTypes, remarks);
			entityBean.setPackagePath(ConfigUtils.read("ftl.mapper.package"));
			String className = NameConverter.toJavaCase(tb);
			entityBean.setClassName(firsetLetterUpper(className));
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("className", entityBean.getClassName());
			data.put("classNameParam", NameConverter.toJavaCase(tb));
			data.put("fieldPrimaryKey", NameConverter.toJavaCase(entityBean.getPrimaryKey()));
			data.put("fixpack", getPackage(entityBean.getClassName()));
			// data.put("mapVar", entityBean.getMap());
			data.put("entityBean", entityBean);
			/*************************************************************/
			List<EntityProperties> list = new ArrayList<EntityProperties>();
			for (int i = 0; i < size; i++) {
				EntityProperties entityProperties = new EntityProperties();
				entityProperties.setColumnNameLower(NameConverter.toJavaCase(colnames[i]));
				entityProperties.setSimpleJavaType(sqlType2JavaType(colTypes[i]));
				entityProperties.setColumnName(colnames[i]);
				entityProperties.setRemarks(remarks[i]);
				entityProperties.setColType(colTypes[i]);
				list.add(entityProperties);
			}
			data.put("columns", list);
			data.put("table", tb);
			data.put("jh", "#");
			data.put("start", "${start}");
			data.put("end", "${end}");
			data.put("entity", entityBean);
			
			data.put("daopackage", ConfigUtils.read("ftl.dao.package"));
			data.put("entitypackage", ConfigUtils.read("ftl.entity.package"));
			data.put("ognlClass", ConfigUtils.read("ognl"));
			entityBean.setFileName(entityBean.getClassName() + "Mapper");
			/*************************************************************/
			freemarkerService.createJavaFile(data, "ftl/"+ConfigUtils.read("ftl.mapper.name") + ".ftl", entityBean, baseConfig,
					freeMarkerConfigurer);

		}
	}

	public static void main(String[] arg) throws Exception {
		String[] configLocation = new String[] { "applicationContext.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		BaseConfig baseConfig = context.getBean(BaseConfig.class);
		FreeMarkerConfigurer freeMarkerConfigurer = context.getBean(FreeMarkerConfigurer.class);
		baseConfig.setEntityPath(ConfigUtils.read("store.path"));
		baseConfig.setMapperXmlPath(ConfigUtils.read("store.path"));
		baseConfig.setSuffix(".xml");
		run(baseConfig, freeMarkerConfigurer);
	}
}
