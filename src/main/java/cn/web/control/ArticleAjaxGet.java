package cn.web.control;

import cn.web.model.Article;
import cn.web.service.ArticleService;
import cn.web.util.LoginState;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleAjaxGet {
	@Autowired
	private ArticleService service;
	@Autowired
	private LoginState loginState;

	public List<Article> getHotArticle() {
		return null;
	}

	@PostMapping({ "/getArticle" })
	@ResponseBody
	public PageInfo<Article> getArticleJson(int num, String user, String topic) {
		PageInfo<Article> pageArticle = null;
		num = num == 0 ? 1 : num;
		if ((null != user) && (null == topic)) {
			pageArticle = getUserArticles(num, user);
		} else if ((null != user) && (null != topic)) {
			pageArticle = getUserTopicArticles(num, user, topic);
		} else if ((null != topic) && (null == user)) {
			pageArticle = getArticlesByTopic(num, topic);
		} else if ((null != topic) && (null != topic)) {
			pageArticle = getUserTopicArticles(num, user, topic);
		} else {
			pageArticle = this.service.getPageArticle(num, 6);
		}
		return pageArticle;
	}

	private PageInfo<Article> getUserArticles(int num, String user) {
		return this.service.getPageArticleByUser(num, 6, user);
	}

	private PageInfo<Article> getArticlesByTopic(int num, String topic) {
		return this.service.getSubjectArticle(num, 6, topic);
	}

	private PageInfo<Article> getUserTopicArticles(int pageNum, String user, String topic) {
		return this.service.getBySubjectAuthor(pageNum, 6, user, topic);
	}
}
