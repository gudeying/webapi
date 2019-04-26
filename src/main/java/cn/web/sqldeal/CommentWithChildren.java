package cn.web.sqldeal;

public class CommentWithChildren {
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
}
