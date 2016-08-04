package com.simple.gcode.utils;

import java.io.Serializable;
/**
 * 
 * @author ldm
 * @Date 2016年8月4日
 */
public class BeanJavaXml implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String fixPackage;
    private String className;
    private String classNameLower;

    public String getClassNameLower()
    {
        return classNameLower;
    }

    public void setClassNameLower(String classNameLower)
    {
        this.classNameLower = classNameLower;
    }

    public String getFixPackage()
    {
        return fixPackage;
    }

    public void setFixPackage(String fixPackage)
    {
        this.fixPackage = fixPackage;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

}
