package com.simple.gcode.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
 


/**
 * 
 * @author ldm
 * @Date 2016年8月4日
 */
public class DataUtils
{

    // private static String TAG = DataUtils.class.getSimpleName();

    public Object convertPrimitiveValue(Class<?> type, String object)
    {

        if (type == Boolean.TYPE)
        {
            return new Boolean(object);
        }
        else if (type == Byte.TYPE)
        {
            return (new Byte(object.getBytes()[0]));
        }
        else if (type == Character.TYPE)
        {
            return (new Character(object.toCharArray()[0]));
        }
        else if (type == Double.TYPE)
        {
            return (new Double(object));
        }
        else if (type == Float.TYPE)
        {
            return (new Float(object));
        }
        else if (type == Integer.TYPE)
        {
            return (new Integer(object));
        }
        else if (type == Long.TYPE)
        {
            return (new Long(object));
        }
        else if (type == Short.TYPE)
        {
            return (new Short(object));
        }
        else
        {
            return object;
        }
    }

    /**
     * "String", "Integer", "Short", "Double", "Boolean", "Date"
     * 对象赋值
     * @param setMethod
     * @param obj
     * @param field
     */
    public Object convertObjectValue(Class<?> type, String object)
    {
        String fieldType = type.getSimpleName();
        if (fieldType.equals("Integer"))
        {
            return NumberUtils.toInt(object, 0);
        }
        if (fieldType.equals("Short"))
        {
            // return NumberUtils.toShort(object, (short) 0);
        }
        if (fieldType.equals("Double"))
        {
            return NumberUtils.toDouble(object, 0);
        }
        if (fieldType.equals("Float"))
        {
            return NumberUtils.toFloat(object, 0f);
        }
        if (fieldType.equals("Long"))
        {
            return NumberUtils.toLong(object, 0l);
        }
        if (fieldType.equals("Boolean"))
        {
            if (!object.equals("null"))
                return new Boolean(object);
            else
                return null;
        }
        if (fieldType.equals("String"))
        {
            return object;
        }
        if (fieldType.equals("Date"))
        {
            if (StringUtils.isNotBlank(object) && !object.equals("null"))
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    return dateFormat.parse(object);
                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else
                return null;

        }
        return object;
    }

    public String convertObjectValue(Class<?> type)
    {
        String fieldType = type.getSimpleName();
        if (fieldType.equalsIgnoreCase("Integer"))
        {
            return "int";
        }
        if (fieldType.equalsIgnoreCase("Short"))
        {
            return "short";
        }
        if (fieldType.equalsIgnoreCase("Double"))
        {
            return "double";
        }
        if (fieldType.equalsIgnoreCase("Float"))
        {
            return "float";
        }
        if (fieldType.equals("Long"))
        {
            return "long";
        }
        if (fieldType.equalsIgnoreCase("Boolean"))
        {
            return "boolean";
        }
        if (fieldType.equalsIgnoreCase("String"))
        {
            return "String";
        }
        if (fieldType.equalsIgnoreCase("Date"))
        {
            return "Date";
        }
        return null;
    }

    /**
     * 格式化时间格式的数据
     * @param f
     * @return
     */
    public static String formatData(Object value)
    {
        boolean isEmpty = StringUtils.isNotEmpty(value + "");
        if (isEmpty)
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 格林尼治标准时间+0800 yyyy", Locale.ENGLISH);
                Date date = sdf.parse(value + "");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.format(new Date(date.getTime()));
            }
            catch (Exception e)
            {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        return null;

    }

    /**
     * javabean 转换为 map
     */
    public static HashMap<String, String> beanToMap(Object object)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        Class<?> clazz = object.getClass();
        Field[] fieldlist = clazz.getDeclaredFields();
        for (Field f : fieldlist)
        {
            if (!Modifier.isStatic(f.getModifiers()))
            {
                try
                {
                    f.setAccessible(true);
                    if (f.getType().getSimpleName().equals("Date"))
                    {
                        map.put(f.getName(), formatData(f.get(object) + ""));
                        continue;
                    }
                    map.put(f.getName(), f.get(object) + "");
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return map;
    }

    /**
     * 根据消费清单类型获取日期
     * @param type
     * @return
     */
    public static String[] getDateByType(String type)
    {
        String[] dateString = new String[2];
        SimpleDateFormat sdfstart = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfend = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        if ("day".equals(type))
        {
            //起始时间
            dateString[0] = sdfstart.format(cal.getTime());
            //结束时间
            dateString[1] = sdfend.format(cal.getTime());
        }
        else if ("week".equals(type))
        {
            //起始时间
            cal = getADayOfWeek(cal, Calendar.MONDAY);
            dateString[0] = sdfstart.format(cal.getTime());
            //结束时间
            cal = Calendar.getInstance();
            cal = getADayOfWeek(cal, Calendar.SUNDAY);
            dateString[1] = sdfend.format(cal.getTime());
        }
        else if ("month".equals(type))
        {//起始时间
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dateString[0] = sdfstart.format(cal.getTime());
            //结束时间
            cal = Calendar.getInstance();
            cal.setTime(new Date());
            int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, value);
            dateString[1] = sdfend.format(cal.getTime());
        }
        else
        {
            return null;
        }
        return dateString;
    }

    /**
     * 获取本周第一天和最后一天方法
     * @param day
     * @param dayOfWeek
     * @return
     */
    private static Calendar getADayOfWeek(Calendar day, int dayOfWeek)
    {
        int week = day.get(Calendar.DAY_OF_WEEK);
        if (week == dayOfWeek)
            return day;
        int diffDay = dayOfWeek - week;
        if (week == Calendar.SUNDAY)
        {
            diffDay -= 7;
        }
        else if (dayOfWeek == Calendar.SUNDAY)
        {
            diffDay += 7;
        }
        day.add(Calendar.DATE, diffDay);
        return day;
    }

    /*public static List<Map<String, String>> getMemberServiceImageList()
    {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 2; i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("shoplocalurl", "");
            map.put("shoplocalurltypename", "会员卡");
            map.put("urlmobileico", "http://www.nadaoshou.com/images/ico/card.png");
            map.put("localcmd", "调用本地会员卡");
            list.add(map);

            map = new HashMap<String, String>();
            map.put("shoplocalurl", "http://www.nadaoshou.com/i/shop/GetTableNoByCode2d.ashx|http://www.nadaoshou.com/i/shop/GetGoodsInfoByCode2d.ashx|http://www.nadaoshou.com/i/shop/UserDoOrder.ashx|http://www.nadaoshou.com/i/shop/UserOrderGoodsList.ashx");
            map.put("shoplocalurltypename", "点餐");
            map.put("urlmobileico", "http://www.nadaoshou.com/images/ico/diancan.png");
            map.put("localcmd", "调用本地点餐-餐厅");
            list.add(map);

            map = new HashMap<String, String>();
            map.put("shoplocalurl", "http://www.nadaoshou.com/i/bill/bout/UserPayApply.ashx");
            map.put("shoplocalurltypename", "结账申请");
            map.put("urlmobileico", "http://www.nadaoshou.com/images/ico/jiezhang.png");
            map.put("localcmd", "调用本地结账-餐厅");
            list.add(map);
        }
        return list;

    }*/

    /**
     * 获取发票抬头
     * @param toolType
     * @return
     */
    public static List<HashMap<String, String>> getUserInvoiceList()
    {

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        int count = 3;
        for (int i = 1; i <= count; i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("invoiceName", i + "北京市朝阳区科技园哈哈哈公司");
            map.put("invoiceId", i + "");
            list.add(map);
        }
        return list;

    }

    /**
     * 增加发票抬头
     * @param toolType
     * @return
     */
    public static List<HashMap<String, String>> addUserInvoice(List<HashMap<String, String>> list, String invoiceName)
    {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("invoiceName", invoiceName);
        map.put("invoiceId", "0");
        list.add(map);
        return list;

    }

    /**
     * 编辑发票抬头
     * @param toolType
     * @return
     */
    public static List<HashMap<String, String>> editUserInvoice(List<HashMap<String, String>> list, String position, String invoiceId, String invoiceName)
    {
        list.get(Integer.parseInt(position)).put("invoiceName", invoiceName);
        return list;

    }

    /**
     * 删除发票抬头
     * @param toolType
     * @return
     */
    public static List<HashMap<String, String>> delInvoiceById(List<HashMap<String, String>> list, int mposition, String invoiceId)
    {

        list.remove(mposition);
        return list;

    }

    /**
     * 获取会员卡数据
     * @return
     */
    public static List<HashMap<String, String>> getCardList(String toolType)
    {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        int count = 6;
        if ("day".equals(toolType))
        {
            count = 1;
        }
        if ("week".equals(toolType))
        {
            count = 2;
        }
        if ("month".equals(toolType))
        {
            count = 3;
        }
        for (int i = 1; i <= count; i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("cardImg", i + "");
            map.put("cardid", i + "");
            list.add(map);
        }
        return list;
    }

    /**
     * 获取会员卡积分数据
     * @return
     */
    public static List<HashMap<String, String>> getCardPointsList(List<HashMap<String, String>> list, int sort)
    {
        for (int i = 1; i <= sort; i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("goodsImg", "http://www.nadaoshou.com/images/shoplogo/lantebojue_logo.jpg");
            map.put("goodsId", i + "");
            map.put("goodsName", i + "法师项链");
            map.put("goodsNo", i + "E-000000");
            map.put("goodsPoints", i + "2000");
            list.add(map);
        }
        return list;
    }

    /**
     * 获取会员卡积分数据
     * @return
     */
    public static Map<Integer, String> getCardPointsType()
    {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        map.put(1, "全部");
        map.put(2, "啊啊");
        map.put(3, "存储");
        //        map.put(4, "版本");
        //        map.put(5, "版本");
        //        map.put(6, "版本");
        //        map.put(7, "版本");
        //        map.put(8, "版本");
        //        map.put(9, "版本");
        //        map.put(10, "版本");
        return map;
    }

}
