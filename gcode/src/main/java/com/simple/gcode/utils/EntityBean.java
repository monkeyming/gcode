package com.simple.gcode.utils;

import java.io.Serializable;

/**
 * 
 * EntityBean.java
 * 
 * @description 表结构实体对象
 * @author ldm
 * @date 2017年2月15日
 */
public class EntityBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columnNames;
	String[] colTypes;
	String tableName;
	String className;
	String mapperName;

	String primaryKey;
	String primaryKeyType;
	// 生成的类文件所存放的包路径
	String packagePath;
	String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getPrimaryKeyType() {
		return primaryKeyType;
	}

	public void setPrimaryKeyType(String primaryKeyType) {
		this.primaryKeyType = primaryKeyType;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String[] getColTypes() {
		return colTypes;
	}

	public void setColTypes(String[] colTypes) {
		this.colTypes = colTypes;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
