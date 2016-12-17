package com.simple.gcode.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * Ognl.java
 * @description 
 * @author ldm
 * @date 2016年12月18日
 */
public class Ognl
{
    /**
     * test for Map,Collection,String,Array isEmpty
     * 
     * @param o
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o, int flag)
        throws IllegalArgumentException
    {
        if (o == null)
            return true;
        if (o instanceof Number)
        {
            if (o instanceof Double)
            {
                if (((Double)o).doubleValue() == 0.0)
                {
                    return true;
                }
            }
            else if (o instanceof Long)
            {
                
                if (((Long)o).longValue() == 0)
                {
                    return true;
                }
                
            }
            else if (o instanceof Integer)
            {
                if (((Integer)o).intValue() == 0)
                {
                    return true;
                }
            }
            else if (o instanceof Float)
            { // heting 2015-6-9
                if (((Float)o).floatValue() == 0.0)
                {
                    return true;
                }
            }
        }
        if (o instanceof String)
        {
            // if (((String) o).isEmpty()() == 0)
            // {
            // return ((String) o).isEmpty();
            return ((String)o).isEmpty();
            // }
        }
        else if (o instanceof Collection)
        {
            if (((Collection)o).isEmpty())
            {
                return true;
            }
        }
        else if (o.getClass().isArray())
        {
            if (Array.getLength(o) == 0)
            {
                return true;
            }
        }
        else if (o instanceof Map)
        {
            if (((Map)o).isEmpty())
            {
                return true;
            }
        }
        else
        {
            return false;
        }
        
        return false;
    }
    
    /**
     * Ĭ�ϵ�����
     * 
     * @param o
     * @return
     */
    /*
     * public static boolean isInsertNotEmpty(Object o) { return !isEmpty(o, 1); }
     */
    
    /**
     * test for Map,Collection,String,Array isNotEmpty
     * 
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object o)
    {
        return !isEmpty(o, 0);
    }
    
    public static boolean isNotBlank(Object o)
    {
        return !isBlank(o);
    }
    
    public static boolean isNumber(Object o)
    {
        if (o == null)
            return false;
        if (o instanceof Number)
        {
            return true;
        }
        if (o instanceof String)
        {
            String str = (String)o;
            if (str.length() == 0)
            {
                str = null;
                return false;
            }
            if (str.trim().length() == 0)
            {
                str = null;
                return false;
            }
            // return org.apache.commons.lang.StringUtils.isNumeric(str);
            str = null;
            return true;// org.apache.commons.lang.StringUtils.isNumeric(str);
        }
        return false;
    }
    
    public static boolean isBlank(Object o)
    {
        if (o == null)
            return true;
        if (o instanceof String)
        {
            // String str = ;
            return isBlank((String)o);
        }
        return false;
    }
    
    public static boolean isBlank(String str)
    {
        if (str == null || str.length() == 0)
        {
            return true;
        }
        
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    
}