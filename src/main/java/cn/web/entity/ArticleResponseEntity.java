package cn.web.entity;

import java.util.Date;

/**
 * 	单条文章具体内容
 * @author kavingu
 *
 */
public class ArticleResponseEntity {
	private boolean code;
	private String message;
	/**
	 *  文章 Article
	 */
	private Object result;
	private long timestamp;
	/**
	 *  获取失败的原因，比如不存在等
	 */
	private String reason;
	/**
	 * 	当前请求用户是否以收藏，未传递用户则收藏参数无效
	 */
	private boolean starStatus;
	
	public ArticleResponseEntity() {
		this.timestamp = new Date().getTime();
		this.message="code:请求是否正确，starStatus：是否收藏，未传递用户收藏参数则无效";
	}

	public boolean isCode() {
		return code;
	}

	public void setCode(boolean code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isStarStatus() {
		return starStatus;
	}

	public void setStarStatus(boolean starStatus) {
		this.starStatus = starStatus;
	}

	
}
