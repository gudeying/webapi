package cn.web.restapi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.web.service.AppKeyService;

@CrossOrigin
@RestController
public class AccessTokenController {
	@Autowired
	TokenUtil tokenUtil;

	private AppKeyService appKeyService;

	@PostMapping("/app/accessToken")
	@ResponseBody
	public Map<String, Object> getToken(@RequestParam("appKey") String appKey,
			@RequestParam("appSecret") String appSecret) {
		Map<String, Object> result = new HashMap<>();
		if (appKeyService.check(appKey, appSecret)) {
			Map<String, String> accessToken = tokenUtil.generateToken();
			result.put("timestamp", new Date().getTime());
			result.put("message", "过期时间：1天");
			result.put("accessKey", accessToken.get("accessKey"));
			result.put("accessToken", accessToken.get("accessToken"));
			result.put("code", true);
			return result;
		}
		result.put("message", "appKey和appSecret验证失败");
		result.put("code", false);
		result.put("timestamp", new Date().getTime());
		return result;

	}

}
