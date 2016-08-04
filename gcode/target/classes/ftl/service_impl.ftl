package ${package}.impl;

import org.springframework.stereotype.Service;
import ${package}.${className}Service;
import ${entitypackage}.${className};

/**
 *  
 * create by Zero
*/
@Service
public class ${className}ServiceImpl implements ${className}Service
{
   	${className}Mapper ${varClassName}Mapper;
   	public int insert(${className} ${varClassName}){
   		return ${varClassName}Mapper.insert(${varClassName});
   	} 

	public ${className} selectByPrimaryKey(long id){
		return ${varClassName}Mapper.selectByPrimaryKey(id);
	} 
	public ${className} selectByPrimaryKey(int id){
		return ${varClassName}Mapper.selectByPrimaryKey(id);
	} 

	public List<${className}> selectAllInfo(){
		return ${varClassName}Mapper.selectAllInfo();
	} 

	public List<${className}> selectPageInfo(HashMap<String,Object> hashMap){
		return ${varClassName}Mapper.selectPageInfo(hashMap);
	} 

	public int update(${className} ${varClassName})
	{
		return ${varClassName}Mapper.update(${varClassName});
	} 

	public int delete(long id){
		return ${varClassName}Mapper.delete(id);
	} 

	public int remove(long id){
		return ${varClassName}Mapper.remove(id);
	} 
	
	public int count(HashMap<String, Object> hashMap){
		return ${varClassName}Mapper.count(hashMap);
	}
}
