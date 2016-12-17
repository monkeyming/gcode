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

	/**
	 * 保存单条数据
	 * 
	 * @param taskDayjobaward
	 * @return
	 */
	int insert(T t);

	/**
	 * 批量增加数据
	 * 
	 * @param t
	 * @return
	 */
	int insertBatch(List<T> t);

	/**
	 * 根据主键查询单条数据
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(long id);

	/**
	 * 根据条件查询数据列表
	 * 
	 * @param hashMap
	 * @return
	 */
	List<T> findByNamedParamList(HashMap<String, Object> hashMap);

	/**
	 * 根据条件查询数据列表
	 * 
	 * @param t
	 * @return
	 */
	List<T> findByNamedParamList(T t);

	/**
	 * 根据对象属性查找满足条件的第一条数据
	 * 
	 * @param t
	 * @return
	 */
	T findByNamedParam(T t);

	/**
	 * 根据对象属性查找满足条件的第一条数据
	 * 
	 * @param hashMap
	 * @return
	 */
	T findByNamedParam(Map<String, Object> hashMap);

	/**
	 * 更新指定对象
	 * 
	 * @param t
	 * @return
	 */
	int update(T t);

	/**
	 * 删除指定数据
	 * 
	 * @param id
	 * @return
	 */
	int delete(long id);

	/**
	 * 根据条件统计数据量
	 * 
	 * @param hashMap
	 * @return
	 */
	long count(HashMap<String, Object> hashMap);

	/**
	 * 根据条件统计数据量
	 * 
	 * @param t
	 * @return
	 */
	long count(T t);

}
