package cn.web.restapi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.web.model.User;
import cn.web.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/app/api/user")
public class UserRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenUtil tokenUtil;

	@PostMapping("/info")
	@ResponseBody
	public Map<String, Object> getUserInfo(
			@RequestHeader("accessToken") String accessToken,
			@RequestHeader("accessKey") String accessKey, 
			@RequestParam("userOpenId") String openId) {
		Map<String, Object> map = new HashMap<>();
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {
			map.put("code", true);
			User user = userService.getUserByOpenid(openId);
			user.setPasw("");
			map.put("user", user);
			map.put("timestamp", new Date().getTime());
		} else {
			map.put("code", false);
			map.put("reason", "token验证失败");
		}
		return map;
	}

	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> loginAccess(@RequestHeader("accessToken") String accessToken,
			@RequestHeader("accessKey") String accessKey, @RequestParam("name") String name,
			@RequestParam("password") String password) {
		Map<String, Object> map = new HashMap<>();
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {

			User user = userService.getUserByName(name);
			if (StringUtils.equals(password, user.getPasw())) {
				map.put("code", true);
				user.setPasw("");
				user.setLevel(0);
				user.setMail("");
				map.put("user", user);
			} else {
				map.put("code", false);
				map.put("user", "");
				map.put("message", "用户名或密码错误");
			}
			return map;
		} else {
			map.put("code", false);
			map.put("message", "Token验证失败");
			return map;
		}

	}
}
