package cn.web.restapi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
	@Autowired
	private RedisTemplate<String, Object>redisTemplate;
	
	public Map<String, String> generateToken() {
		String token = UUID.randomUUID().toString();
		String key = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(key, token);
		redisTemplate.expire(key, 1, TimeUnit.HOURS);
		Map<String, String> map = new HashMap<>(2);
		map.put("accessKey", key);
		map.put("accessToken", token);
		System.out.println(key);
		System.out.println(token);
		return map;
	}
	
	public boolean tokenValidation(@NotNull String accessKey,String accessToken) {
		String realToken = (String) redisTemplate.opsForValue().get(accessKey);
		return StringUtils.equals(accessToken, realToken);
	}
}
