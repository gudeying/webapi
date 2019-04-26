package cn.web.control;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.web.model.User;
import cn.web.service.UserService;
import cn.web.util.CookieUtil;

@RestController
public class AjaxLoginController {
	@Autowired
	private UserService userService;

	@PostMapping({ "/ajaxLogin" })
	@ResponseBody
	public Map<String, Object> ajaxLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		User user = this.userService.getUserByName(username);
		Map<String, Object> result = new HashMap<>();
		if ((user != null) && (password.trim().equals(user.getPasw()))) {
			CookieUtil cookie = new CookieUtil();
			try {
				cookie.addLoginState(Boolean.valueOf(false), username, user.getId(), response, request);
				result.put("result", Boolean.valueOf(true));
				result.put("message", "欢迎你，" + user.getNickName());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				result.put("result", Boolean.valueOf(false));
				result.put("message", "登陆失败！请稍后重试");
			}
		} else {
			result.put("result", Boolean.valueOf(false));
			result.put("message", "用户名或密码错误");
		}
		return result;
	}

	@PostMapping({ "/ajaxLogout" })
	@ResponseBody
	public Map<String, Object> ajaxLogOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("user");
		Map<String, Object> result = new HashMap<>();
		result.put("result", Boolean.valueOf(true));
		return result;
	}
}
