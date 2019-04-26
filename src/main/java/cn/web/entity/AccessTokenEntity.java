package cn.web.entity;

public class AccessTokenEntity {
	private String accessKey;
	private String accessToken;
	private long timestamp;

	public String getAccessKey() {
		return accessKey;
	}

	public AccessTokenEntity() {
	}

	public AccessTokenEntity(String accessKey, String accessToken, long timestamp) {
		this.accessKey = accessKey;
		this.accessToken = accessToken;
		this.timestamp = timestamp;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
