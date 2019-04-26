package cn.web.sqldeal;

import cn.web.model.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public abstract interface CommentDeal
{
  @Select({"select * from comment where id = #{id}"})
  public abstract Comment getCommentById(int paramInt);
  
  @Select({"select * from comment where articleid = #{articleid}"})
  public abstract List<Comment> getAllComments();
  
  @Select({"select * from comment where articleid = #{articleid} and parentid =0 order by id desc limit 0,5"})
  public abstract List<Comment> getLast5Comments(int paramInt);
  
  @Insert({"insert into comment(content,user,articleid,time,parentid,userid,towho) values(#{content},#{user},#{articleid},#{time},#{parentid},#{userid},#{towho})"})
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  public abstract void save(Comment paramComment);
  /**
   * 获取子评论
   * @return
   */
  @Select("select * from comment where parentid=#{id} limit 0,3 order by id desc")
  public List<Comment> get3InnerComment(int id);
  
  @Select("select * from comment where parentid =#{id} order by id desc")
  public List<Comment> getAllChildrenComment(int id);
  
  @Select("select * from comment where parentid=#{id}")
  public List<Comment> getAllByParentId(int id);
  
}
