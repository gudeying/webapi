package cn.web.model;

public class Comment {
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
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getArticleid() {
		return this.articleid;
	}

	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}

	public String getTime() {
		return this.time;
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
}
