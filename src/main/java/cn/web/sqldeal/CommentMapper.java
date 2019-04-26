package cn.web.sqldeal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.web.model.Comment;

public interface CommentMapper {
	/**
	 * 列表的时候只显示前五条评论，子评论显示三条
	 * @return
	 */
	public List<NewComment> get5Comments(@Param
			("articleId")int articleId);
	/**
	 * 获取所有的评论，子评论也是三条，分页插件管理分页
	 * @return
	 */
	public List<NewComment> getAllComments(@Param("articleId")int articleid);
	/**
	 * 根据id获取一条评论的详情。子评论暂时限定为8条
	 * @param id
	 * @return
	 */
	public NewComment getComentById(int id);
}
