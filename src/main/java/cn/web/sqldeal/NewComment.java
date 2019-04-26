package cn.web.sqldeal;

import java.util.List;

import cn.web.model.Comment;

public class NewComment {
	private int id;
	private String content;
	private String user;
	private int articleid;
	private String time;
	/**
	 * 上一级的commentId
	 */
	private int parentid;
	private int userid;
	/**
	 * 回复谁
	 */
	private String towho;
	private int childrenComSize ;
	private List<Comment> childrenComments;

	public int getChildrenComSize() {
		return childrenComments.size();
	}

	public void setChildrenComSize(int childrenComSize) {
		this.childrenComSize = childrenComSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getArticleid() {
		return articleid;
	}

	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getTowho() {
		return towho;
	}

	public void setTowho(String towho) {
		this.towho = towho;
	}

	public List<Comment> getChildrenComments() {
		return childrenComments;
	}

	public void setChildrenComments(List<Comment> childrenComments) {
		this.childrenComments = childrenComments;
	}
	
	
	
}
