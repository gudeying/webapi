package cn.web.newController;

import cn.web.model.ResultMap;
import cn.web.model.User;
import cn.web.service.SubscribeService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxSubscribleController {
	@Autowired
	private UserService userService;
	@Autowired
	private LoginState loginState;
	@Autowired
	private SubscribeService subscribeService;

	@PostMapping({ "subscribe" })
	public ResultMap<String> subscribe(HttpServletRequest request, @RequestParam("targetUser") int targetUser) {
		ResultMap<String> resultMap = new ResultMap<>();
		if (this.loginState.isIslogin(request)) {
			User currentUser = this.loginState.getLoginedUser(request);
			this.subscribeService.subscribe(currentUser.getId(), targetUser);
			resultMap.setResult(Boolean.valueOf(true));
		} else {
			resultMap.setResult(Boolean.valueOf(false));
		}
		return resultMap;
	}

	@PostMapping({ "/unSubscribe" })
	public Map unSubscribe(HttpServletRequest request, @RequestParam("targetUser") int targetUser) {
		Map<String, Object> resultMap = new HashMap<>();
		if (this.loginState.isIslogin(request)) {
			User currentUser = this.loginState.getLoginedUser(request);
			this.subscribeService.unSubscribe(currentUser.getId(), targetUser);
			resultMap.put("result", Boolean.valueOf(true));
		} else {
			resultMap.put("result", Boolean.valueOf(false));
		}
		return resultMap;
	}
}
