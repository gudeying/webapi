package cn.web.sqldeal;

import cn.web.model.Gallery;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public abstract interface GalleryDeal
{
  @Select({"select * from gallery where 1=1 order by id desc limit 6"})
  public abstract List<Gallery> get6Gallery();
  
  @Select({"select * from gallery where 1=1 limit 15"})
  public abstract List<Gallery> get15Gallery();
  
  @Select({"select * from gallery order by id desc"})
  public abstract List<Gallery> getAllGallery();
  
  @Select({"select * from gallery where user = #{username} order by id desc"})
  public abstract List<Gallery> get15GalleryByUser(String paramString);
  
  @Insert({"insert into gallery(des,src,comsrc,user) values(#{des},#{src},#{comsrc},#{user})"})
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  public abstract void save(Gallery paramGallery);
  
  @Select({"select * from gallery where id=#{id}"})
  public abstract Gallery getById(int paramInt);
  
  @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
  @Select({"delete from gallery where id=#{id}"})
  public abstract void deleteById(int paramInt);
}
