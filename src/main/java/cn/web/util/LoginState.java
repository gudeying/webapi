package cn.web.util;

import cn.web.model.User;
import cn.web.service.EnshrineService;
import cn.web.service.SubscribeService;
import cn.web.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginState {
	@Autowired
	private UserService userService;
	@Autowired
	private EnshrineService enshrineService;
	@Autowired
	private SubscribeService subscribeService;

	public boolean isIslogin(HttpServletRequest request) {
		return ((Boolean) getUserState(request).get("islogin")).booleanValue();
	}

	public User getLoginedUser(HttpServletRequest request) {
		return this.userService.getUserByName((String) getUserState(request).get("username"));
	}
	
	public static String getDateFormatted() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
		String time = sdf.format(date);
		return time;
	}
	public Map<String, Object> getUserState(HttpServletRequest req) {
		Boolean islogin = Boolean.valueOf(false);
		Map<String, Object> m = new HashMap<>();
		HttpSession session = req.getSession(false);
		if ((session != null) && (session.getAttribute("user") != null)) {
			m.put("username", session.getAttribute("user").toString());
			islogin = Boolean.valueOf(true);
		}
		m.put("islogin", islogin);
		return m;
	}

	public Model addLoginMessage(Model model, HttpServletRequest request) {
		Boolean islogin = Boolean.valueOf(isIslogin(request));
		model.addAttribute("islogin", islogin);
		if (islogin.booleanValue()) {
			User user = getLoginedUser(request);
			model.addAttribute("user", user);
			model.addAttribute("collectionNum",
					Integer.valueOf(this.enshrineService.getCount(String.valueOf(user.getId()))));
			model.addAttribute("SubscribeToRead", Integer.valueOf(this.subscribeService.toReadeSize(user.getId())));
		}
		return model;
	}
}
