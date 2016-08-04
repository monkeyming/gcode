package com.simple.gcode.utils;

import java.io.Serializable;

/**
 * 
 * @author ldm
 *
 */
public class BaseConfig implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 
     * 扩展名称
     */
    private String suffix;
    /**
     * 实体类
     */
    private String entityPath;
    /**
     * mapper-xml 对应关系
     */
    private String mapperXmlPath;

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public String getEntityPath()
    {
        return entityPath;
    }

    public void setEntityPath(String entityPath)
    {
        this.entityPath = entityPath;
    }

    public String getMapperXmlPath()
    {
        return mapperXmlPath;
    }

    public void setMapperXmlPath(String mapperXmlPath)
    {
        this.mapperXmlPath = mapperXmlPath;
    }

}
