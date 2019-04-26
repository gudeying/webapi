package cn.web.sqldeal;

import cn.web.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public abstract interface UserMappi
{
  @Select({"select * from user where id = #{id}"})
  public abstract User getUserById(int paramInt);
  
  @Select({"select * from user where name = #{name} limit 0,1"})
  public abstract User getUserByName(String paramString);
  
  @Select({"select pasw from user where name = #{name}"})
  public abstract String getPaswByName(String paramString);
  
  @Delete({"delete * from user where id = #{id}"})
  public abstract void deleteUserById(int paramInt);
  
  @Select({"select * from user where openid = #{openid}"})
  public abstract User getUserByOpenid(String paramString);
  
  @Select({"select * from user where mail = #{mail}"})
  public abstract User getUserByMailBox(String paramString);
  
  @Update({"update user set level = #{level} where mail = #{mail}"})
  public abstract void updateUserLevel(User paramUser);
  
  @Insert({"insert into user(name,age,sex,userlogo,pasw,openid,mail,level,autograph,nickname) values(#{name},#{age},#{sex},#{userlogo},#{pasw},#{openid},#{mail},#{level},#{autograph},#{nickName})"})
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  public abstract void save(User paramUser);
}
