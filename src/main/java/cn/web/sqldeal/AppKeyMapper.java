package cn.web.sqldeal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.web.model.Application;

@Mapper
public interface AppKeyMapper {
	
	@Select("select * from application where appKey = #{appKey}")
	public Application getAppByAppKey(String appKey);
	
	@Insert("insert into application(appKey,appSecret,appName,state,licence,createDate) values(#{appKey},#{appSecret},#{appName},#{state},#{licence},#{createDate})")
	public int insert(Application app);

}
