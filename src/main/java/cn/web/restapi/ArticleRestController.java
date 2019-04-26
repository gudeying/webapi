package cn.web.restapi;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import cn.web.entity.ArticleResponseEntity;
import cn.web.entity.CommonResponseEntity;
import cn.web.model.Article;
import cn.web.model.ArticleHead;
import cn.web.model.User;
import cn.web.service.ArticleService;
import cn.web.service.EnshrineService;
import cn.web.service.UserService;

@RestController
public class ArticleRestController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	private EnshrineService enshrineService;

	@PostMapping("/app/api/article/list")
	@ResponseBody
	public ResultBody getOnePage(HttpServletRequest request, @RequestParam("pageNum") int pageNum,
			@RequestParam(value = "userOpenId", required = false) String userOpenId,
			@RequestParam(value = "topic", required = false) String topic,
			@RequestHeader(value = "accessToken") String accessToken,
			@RequestHeader(value = "accessKey", required = true) String accessKey) {
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {

			int authorId = 0;
			if (null != userOpenId) {
				authorId = userService.getUserByOpenid(userOpenId).getId();
			}
			Object data = ArticleUtils.getArticles(articleService, authorId, topic, pageNum, 6);
			return ResultBody.success(data);
		} else {
			return ResultBody.tokenFail();
		}
	}

	@PostMapping("/app/api/article/info")
	@ResponseBody
	public ArticleResponseEntity getArticleDetails(@RequestHeader("accessKey") String accessKey,
			@RequestHeader("accessToken") String accessToken, @RequestParam("id") int id,
			@RequestParam(value = "userOpenId", required = false) String openId) {
		ArticleResponseEntity entity = new ArticleResponseEntity();
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {
			entity.setCode(true);
			Article article = articleService.getArticleById(id);
			entity.setResult(article);
			if (null != openId) {
				int userId = userService.getUserByOpenid(openId).getId();
				entity.setStarStatus(enshrineService.isEnshrine(String.valueOf(userId), String.valueOf(id)));
			}
		} else {
			entity.setCode(false);
			entity.setReason("token错误");
		}
		return entity;
	}

	/**
	 * 
	 * @param accessKey
	 * @param accessToken
	 * @param id
	 * @param openId
	 * @param opt         star收藏或者unstar取消收藏
	 * @return
	 */
	@PostMapping("/app/api/article/starOpt")
	@ResponseBody
	public CommonResponseEntity star(@RequestHeader("accessKey") String accessKey,
			@RequestHeader("accessToken") String accessToken, @RequestParam("id") int id,
			@RequestParam("userOpenId") String openId, @RequestParam("opt") String opt) {
		CommonResponseEntity entity = new CommonResponseEntity();
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {
			entity.setCode(true);
			String userId = String.valueOf(userService.getUserByOpenid(openId).getId());
			if (StringUtils.equalsIgnoreCase("star", opt)) {
				enshrineService.shoucang(userId, String.valueOf(id));
			} else if (StringUtils.equalsIgnoreCase("unStar", opt)) {
				enshrineService.cancelEnshrine(userId, String.valueOf(id));
			}
			entity.setResultCode(1);
		} else {
			entity.setCode(false);
			entity.setReason("token错误！");
		}
		return entity;
	}

	@PostMapping("/app/api/article/search")
	@ResponseBody
	public Map<String, Object> search(
			@RequestHeader("accessKey") String accessKey,
			@RequestHeader("accessToken") String accessToken, 
			@RequestParam("keyWord") String keyWord,
			@RequestParam(value = "pageNum", required = false) int num,
			@RequestParam(value = "pageSize", required = false) int size) {
		PageInfo<ArticleHead> pageInfo = null;
		Map<String, Object> map = new HashMap<>();
		int pageNum = 0 == num ? num : 1;
		int pageSize = 0 == size ? size : 6;
		if (tokenUtil.tokenValidation(accessKey, accessToken)) {
			pageInfo = articleService.searchByTitle(pageNum, pageSize, keyWord);
			map.put("code", true);
			map.put("result", pageInfo);
		} else {
			map.put("code", false);
			map.put("message", "token 验证失败");
		}
		return map;
	}

	@PostMapping("/app/api/article/getList")
	@ResponseBody
	public ResultBody getList(DataModel model) {
		return new ResultBody();
	}
	public ResultBody comment(
			@RequestHeader("accessKey") String accessKey,
			@RequestHeader("accessToken") String accessToken, 
			@RequestParam("keyWord") String keyWord,
			@RequestParam("userId")String userId,
			@RequestParam("articleId")int articleId,
			@RequestParam("comment")String comment) {
		
		ResultBody resultBody = new ResultBody();
		return resultBody;
	}

}
