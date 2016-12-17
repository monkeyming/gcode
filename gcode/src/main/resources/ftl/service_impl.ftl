package ${package}.impl;
import javax.annotation.Resource;
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
	@Resource
   	${className}Mapper ${varClassName}Mapper;
   	public int insert(${className} ${varClassName}){
   		return ${varClassName}Mapper.insert(${varClassName});
   	} 

	public ${className} selectByPrimaryKey(long id){
		return ${varClassName}Mapper.selectByPrimaryKey(id);
	} 
	

	public ${className} findByNamedParam(HashMap<String,Object> hashMap){
		return ${varClassName}Mapper.findByNamedParam(hashMap);
	} 
	public List<${className}> findAll(HashMap<String,Object> hashMap){
		return ${varClassName}Mapper.findAll(hashMap);
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
