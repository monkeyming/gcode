<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daopackage}.${className}Mapper">

   <resultMap id="BaseResultMap" type="${entitypackage}.${className}">
      <#list columns as column>
      <#if  column.columnName == entity.primaryKey>
      <id column="${column.columnName}" property="${column.columnNameLower}"   jdbcType="${column.colType}"/>
      <#else>
      <result column="${column.columnName}" property="${column.columnNameLower}"  jdbcType="${column.colType}"/>
      </#if>
      </#list>
     <#if  mapVar?exists>
	    <#list mapVar?keys as key> 
	  <association  property="${mapVar[key]}" column="${entityBean.primaryKey}" resultMap="${key}Mapper.BaseResultMap"/>
	    </#list>
    </#if>
   </resultMap>
  
  <insert id="insertBatch" parameterType="ArrayList">
	    insert into ${table}  (<#list columns as column><#if column_has_next>${column.columnName},<#else>${column.columnName}</#if></#list>)  values 
	    <foreach collection="list" item="obj" index="index" separator="," >
	        (<#list columns as column><#if column_has_next>${jh}{obj.${column.columnNameLower}},<#else>${jh}{obj.${column.columnNameLower}}</#if></#list>)
	    </foreach>
   </insert>
   
  <insert id="insert" parameterType="${entitypackage}.${className}" useGeneratedKeys="true">
	  insert into ${table}   
      <trim prefix="(" suffix=")" suffixOverrides=",">
      <#list columns as column>
         <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">${column.columnName},</if>
      </#list>
      </trim>
      <trim prefix=" values (" suffix=")" suffixOverrides=",">
      <#list columns as column>
          <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">${jh}{${column.columnNameLower},jdbcType=${column.colType}},</if>
      </#list>    
      </trim>
   </insert>
   
      
   
   <update id="update" parameterType="${entitypackage}.${className}"> 
      update ${table} 
       <set>
       <#list columns as column>
          <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">${column.columnName} = ${jh}{${column.columnNameLower},jdbcType=${column.colType}},</if>
       </#list>  
      </set>
      where ${entity.primaryKey} =  ${jh}{${fieldPrimaryKey},jdbcType=BIGINT}
   </update>
   
   <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
	select *  from ${table} where ${entity.primaryKey} = ${jh}{${fieldPrimaryKey},jdbcType=BIGINT}   limit 1
   </select>
   
   <select id="findByNamedParam" resultMap="BaseResultMap">
	select *  from ${table}  
	<trim prefix="where" prefixOverrides="and |or "> 
	    <#list columns as column>
          <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">and ${column.columnName} = ${jh}{${column.columnNameLower},jdbcType=${column.colType}}</if>
       </#list>  
	</trim>
    order  by  ${entity.primaryKey}  desc    limit 1
   </select>
   <select id="count"  resultType="int">
	 select count(*)  from ${table}   
	 <trim prefix="where" prefixOverrides="and |or "> 
	    <#list columns as column>
          <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">and ${column.columnName} = ${jh}{${column.columnNameLower},jdbcType=${column.colType}}</if>
       </#list>  
	</trim>
   </select>
   
   <delete id="delete" parameterType="java.lang.Long">
	delete  from ${table} where  ${entity.primaryKey} =  ${jh}{${fieldPrimaryKey},jdbcType=BIGINT}
   </delete>
	
 
   
   <select id="findByNamedParamList" resultMap="BaseResultMap">
    select *  from   ${table}
    <trim prefix="where" prefixOverrides="AND |OR "> 
	   <#list columns as column>
          <if test="@${ognlClass}@isNotEmpty(${column.columnNameLower})">and ${column.columnName} = ${jh}{${column.columnNameLower},jdbcType=${column.colType}}</if>
       </#list>
	</trim>  order  by  ${entity.primaryKey}  desc  
   </select>
</mapper>
