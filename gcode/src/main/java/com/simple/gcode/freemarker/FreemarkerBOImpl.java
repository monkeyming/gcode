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
import com.simple.gcode.utils.NameConverter;
import com.simple.gcode.utils.Utilities;

/**
 * 
 * FreemarkerBOImpl.java
 * @description 用于生成service接口的实现类的 无需配置 
 * @author ldm
 * @date 2016年12月18日
 */
public class FreemarkerBOImpl extends FreemarkerService {
	public static void run(BaseConfig baseConfig, FreeMarkerConfigurer freeMarkerConfigurer) throws Exception {
		FreemarkerService freemarkerService = new FreemarkerEntityImpl();

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
			String className = NameConverter.toJavaCase(tb);
			entityBean.setClassName(firsetLetterUpper(className));
			setEntityBean(dbmd, rsmd, entityBean, tb, size, colnames, colTypes, remarks);
			String implPackage = ConfigUtils.read("ftl.service.package") + ".impl";
			entityBean.setPackagePath(implPackage);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("className", entityBean.getClassName());
			data.put("fixpack", getPackage(entityBean.getClassName()));
			data.put("classNameParam", NameConverter.toJavaCase(tb));
			data.put("primaryKey", NameConverter.toJavaCase(entityBean.getPrimaryKey()));

			data.put("varClassName", Utilities.captureVarName(entityBean.getClassName()));
			data.put("implpackage", implPackage);
			data.put("entitypackage", ConfigUtils.read("ftl.entity.package"));
			data.put("daopackage", ConfigUtils.read("ftl.dao.package"));
			data.put("package", ConfigUtils.read("ftl.service.package"));
			/*************************************************************/
			entityBean.setFileName(entityBean.getClassName() + "ServiceImpl");
			/*************************************************************/
			// freemarkerService.createJavaFile(data, "service_impl.ftl",
			// entityBean);
			freemarkerService.createJavaFile(data, "ftl/" + ConfigUtils.read("ftl.service.name") + "_impl.ftl",
					entityBean, baseConfig, freeMarkerConfigurer);

		}
	}

	public static void main(String[] arg) throws Exception {

		String[] configLocation = new String[] { "applicationContext.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		BaseConfig baseConfig = context.getBean(BaseConfig.class);
		FreeMarkerConfigurer freeMarkerConfigurer = context.getBean(FreeMarkerConfigurer.class);
		baseConfig.setEntityPath(ConfigUtils.read("store.path"));
		baseConfig.setMapperXmlPath(ConfigUtils.read("store.path"));
		baseConfig.setSuffix(".java");
		run(baseConfig, freeMarkerConfigurer);
	}
}
