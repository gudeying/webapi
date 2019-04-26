package cn.web.sqldeal;

import cn.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public abstract interface UpdateMapper
{
  @UpdateProvider(type=DynamicSqlProvider.class, method="updateUserSql")
  public abstract int updateuser(User paramUser);
}
