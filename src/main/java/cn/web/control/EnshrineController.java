package cn.web.control;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.EnshrineService;
import cn.web.service.SubscribeService;
import cn.web.service.UserService;
import cn.web.util.LoginState;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * article star
 * 
 * @author kavingu
 *
 */
@RestController
public class EnshrineController {
	@Autowired
	private EnshrineService service;
	@Autowired
	private SubscribeService subscribeService;
	@Autowired
	private LoginState loginState;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;

	@PostMapping({ "/getEnshrinedArticles" })
	@ResponseBody
	public List<Article> getEnshrinedArticles(@RequestParam("userId") String userid) {
		return this.service.getEnshrinedArticle(userid);
	}

	@PostMapping({ "/isEnshrined" })
	@ResponseBody
	public Map<String, Object> isChouCang(@RequestParam("userId") String userId,
			@RequestParam("articleId") String articleId, HttpServletRequest request) {
		Map<String, Object> loginStateMap = this.loginState.getUserState(request);
		Map<String, Object> result = new HashMap<>();
		if (((Boolean) loginStateMap.get("islogin")).booleanValue()) {
			Boolean isensh = Boolean.valueOf(this.service.isEnshrine(userId, articleId));
			result.put("state", "success");
			result.put("result", isensh);
			return result;
		}
		result.put("state", "failed");
		result.put("result", Boolean.valueOf(false));
		return result;
	}

	@PostMapping({ "/isEnshrinedAndSubscribed" })
	@ResponseBody
	public Map<String, Object> isChouCangAndSubscribed(@RequestParam(value = "userId",required = false) String userId,
			@RequestParam("articleId") String articleId, @RequestParam("ownerId") String ownerId,
			HttpServletRequest request) {
		Map<String, Object> loginStateMap = this.loginState.getUserState(request);
		Map<String, Object> result = new HashMap<>();
		int num = articleService.getStarNum(Integer.parseInt(articleId));
		result.put("starNum",num);
		if (((Boolean) loginStateMap.get("islogin")).booleanValue()&& null !=userId) {
			String username = (String) loginStateMap.get("username");
			User user = this.userService.getUserByName(username);
			Boolean isensh = Boolean.valueOf(this.service.isEnshrine(userId, articleId));
			Boolean isSubscribed = this.subscribeService.isSubscribe(Integer.parseInt(ownerId), user.getId());
			result.put("isChouCang", isensh);
			result.put("isSubscribed", isSubscribed);
			result.put("result", Boolean.valueOf(true));
			return result;
		}
		result.put("state", "failed");
		result.put("result", Boolean.valueOf(false));
		return result;
	}

	@PostMapping({ "/shoucang" })
	@ResponseBody
	public Map<String, Object> enshrine(HttpServletRequest request, @RequestParam("articleId") String articleId) {
		Map<String, Object> loginStateMap = this.loginState.getUserState(request);
		Map<String, Object> result = new HashMap<>();
		if (((Boolean) loginStateMap.get("islogin")).booleanValue()) {
			String username = (String) loginStateMap.get("username");
			User user = this.userService.getUserByName(username);
			this.service.shoucang(String.valueOf(user.getId()), articleId);
			result.put("state", "success");
			result.put("result", Boolean.valueOf(true));
			return result;
		}
		result.put("state", "failed");
		result.put("result", Boolean.valueOf(false));
		return result;
	}

	@PostMapping({ "/quxiao" })
	@ResponseBody
	public Map<String, Object> cancelEnshrine(HttpServletRequest request, @RequestParam("userId") String userId,
			@RequestParam("articleId") String articleId) {
		Map<String, Object> loginStateMap = this.loginState.getUserState(request);
		Map<String, Object> result = new HashMap<>();
		if (((Boolean) loginStateMap.get("islogin")).booleanValue()) {
			String username = (String) loginStateMap.get("username");
			User user = this.userService.getUserByName(username);
			if (username.equals(user.getName())) {
				this.service.cancelEnshrine(userId, articleId);
				result.put("result", Boolean.valueOf(true));
				return result;
			}
			result.put("result", Boolean.valueOf(false));
			return result;
		}
		result.put("result", Boolean.valueOf(false));
		return result;
	}

}
