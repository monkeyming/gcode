package com.simple.gcode.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础mapper接口类
 * 
 * @author ldm
 * @Date 2016年7月14日
 * @param <T>
 */
public interface BaseMapper<T> {

	int insert(T taskDayjobaward);

	T selectByPrimaryKey(long id);

	List<T> findByNamedParamList(HashMap<String, Object> hashMap);

	// 根据对象属性查找满足条件的第一条数据
	T findByNamedParam(T t);

	// 根据map取出符合条件的第一条数据
	T findByNamedParam(Map<String, Object> hashMap);

	int update(T taskDayjobaward);

	int delete(long id);

	int remove(long id);

	int count(HashMap<String, Object> hashMap);

}
