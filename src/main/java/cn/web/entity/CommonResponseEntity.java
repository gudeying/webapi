package cn.web.entity;

import java.util.Date;
/**
 * 	远程调用执行结果返回的数据，例如执行用户收藏、修改用户信息等操作
 * @author kavingu
 *
 */
public class CommonResponseEntity {
	private boolean code;
	private String message;
	private String reason;
	private long timestamp;
	private int resultCode;
	
	public CommonResponseEntity() {
		this.timestamp=new Date().getTime();
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
}
