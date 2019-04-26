package cn.web.sqldeal;

import cn.web.model.Blog;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public abstract interface BlogDeal
{
  @Select({"select * from blog where title = #{title}"})
  public abstract Blog getBlogByTitle(String paramString);
  
  @Select({"select * from blog where id = #{id}"})
  public abstract Blog getBlogById(int paramInt);
  
  @Select({"select * from blog order by id desc"})
  public abstract List<Blog> getAllBlogs();
  
  @Select({"select * from blog order by id desc limit 0,3"})
  public abstract List<Blog> getLast3Blog();
  
  @Select({"select * from blog order by zan desc limit 0,3"})
  public abstract List<Blog> getMost3Blog();
  
  @Delete({"delete  from blog where id = #{id}"})
  public abstract void deleteBlogById(int paramInt);
  
  @Insert({"insert into blog(author,title,detail,time,emotion,zan,src) values(#{author},#{title},#{detail},#{time},#{emotion},#{zan},#{src})"})
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  public abstract void save(Blog paramBlog);
  
  @Select({"select * from blog where author = #{username} order by id desc"})
  public abstract List<Blog> getBlogByUser(String paramString);
}
