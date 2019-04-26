package cn.web.service;

import cn.web.model.Comment;
import cn.web.sqldeal.CommentDeal;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CommentService")
public class CommentService {
	@Autowired
	public CommentDeal deal;

	public Comment getCommentById(int id) {
		return this.deal.getCommentById(id);
	}

	public List<Comment> getAllComments() {
		return this.deal.getAllComments();
	}

	public List<Comment> getLast5Comments(int articleid) {
		return this.deal.getLast5Comments(articleid);
	}

	public void save(Comment c) {
		this.deal.save(c);
	}
	
	public List<Comment> get3ChildrenComment(int id){
		return deal.get3InnerComment(id);
	}
	
	public List<Comment> getCommentByParentId(int id) {
		return deal.getAllByParentId(id);
	}

	public PageInfo<Comment> getPageChildrenComment(int parentId,int pageNum,int pageSize){
		PageHelper.startPage(pageNum,pageSize);
		List<Comment>comments = deal.getAllChildrenComment(parentId);
		PageInfo<Comment>pageInfo = new PageInfo<>(comments);
		return pageInfo;
	}
}
