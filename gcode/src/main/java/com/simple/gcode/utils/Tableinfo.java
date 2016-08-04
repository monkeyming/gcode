package com.simple.gcode.utils;

import java.io.Serializable;

/**
 * mybatis xml 表中的基本信息
 * @author ldm
 *
 */
public class Tableinfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String javaName;
    private String jdbcType;//mybatis 数据类型
    private String operType = "=";//等于，大于小于，like
    private String aliasJavaName; //like  in 查询条件用到

    public String getAliasJavaName()
    {
        return aliasJavaName;
    }

    public void setAliasJavaName(String aliasJavaName)
    {
        this.aliasJavaName = aliasJavaName;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getJavaName()
    {
        return javaName;
    }

    public void setJavaName(String javaName)
    {
        this.javaName = javaName;
    }

    public String getOperType()
    {
        return operType;
    }

    public void setOperType(String operType)
    {
        this.operType = operType;
    }

    public String getJdbcType()
    {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType)
    {
        this.jdbcType = jdbcType;
    }

    @Override
    public String toString()
    {
        return "Tableinfo [fieldName=" + fieldName + ", javaName=" + javaName + ", jdbcType=" + jdbcType + ", operType=" + operType + "]";
    }

}
