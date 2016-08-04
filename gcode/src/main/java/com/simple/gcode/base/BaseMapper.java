package com.simple.gcode.base;

import java.util.HashMap;
import java.util.List;

/**
 * 基础mapper接口类
 * @author ldm
 * @Date 2016年7月14日
 * @param <T>
 */
public interface BaseMapper<T> 
{

	int insert(T taskDayjobaward); 

	T selectByPrimaryKey(long id); 

	List<T> selectAllInfo(); 

	List<T> selectPageInfo(HashMap<String,Object> hashMap); 

	int update(T taskDayjobaward); 

	int delete(long id); 

	int remove(long id); 
	
	int count(HashMap<String, Object> hashMap);

}
