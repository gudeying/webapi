package cn.web.restapi;

import java.io.Serializable;

public class ResultBody implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean result;
	private String message;

	private Object body;
	private boolean success;
	private long code;

	public static ResultBody success(Object data) {
		ResultBody resultBody = new ResultBody();
		resultBody.setBody(data);
		resultBody.setCode(300);
		resultBody.setMessage("success");
		resultBody.setResult(true);
		resultBody.setSuccess(true);
		return resultBody;
	}

	public static ResultBody fail(String message, long code) {
		ResultBody resultBody = new ResultBody();

		resultBody.setCode(code != 0 ? code : 400);
		resultBody.setMessage(message != null ? message : "success");
		resultBody.setResult(false);
		resultBody.setSuccess(false);
		return resultBody;
	}

	public static ResultBody tokenFail() {
		ResultBody resultBody = new ResultBody();
		resultBody.setCode(403);
		resultBody.setMessage("accessToken vilidate failed");
		resultBody.setResult(false);
		resultBody.setSuccess(false);
		return resultBody;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

}
