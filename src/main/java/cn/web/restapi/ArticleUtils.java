package cn.web.restapi;

import com.github.pagehelper.PageInfo;

import cn.web.model.Article;
import cn.web.model.ArticleHead;
import cn.web.service.ArticleService;

public class ArticleUtils {
	public static PageInfo<ArticleHead> getArticles(ArticleService articleService, 
			int authorId, 
			String topic, 
			int pageNum,
			int pageSize) {
		if (0 != authorId && null != topic) {
			return articleService.getPageUserTopicArticleHead(authorId, topic, pageNum, pageSize);
		}
		if (authorId == 0 && null != topic) {
			return articleService.getPageTopicArticleHead(topic, pageNum, pageSize);
		}
		if (authorId == 0 && null == topic) {
			return articleService.getPageArticleHead(pageNum, pageSize);
		}
		if (authorId !=0&& null == topic) {
			return articleService.getUserPageArticleHead(authorId, pageNum, pageSize);
		}
		return articleService.getPageArticleHead(pageNum, pageSize);

	}
}
