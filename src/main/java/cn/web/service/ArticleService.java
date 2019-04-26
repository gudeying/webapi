package cn.web.service;

import cn.web.model.Article;
import cn.web.model.ArticleHead;
import cn.web.sqldeal.ArticleDeal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ArticleService")
public class ArticleService {
	@Autowired
	public ArticleDeal deal;

	public Article getArticleById(int id) {
		return this.deal.getArticleById(id);
	}

	public PageInfo<Article> getPageArticle(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getAllArticles();
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}
	
	public PageInfo<ArticleHead> getPageArticleHead(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleHead> articles = this.deal.getArticleHeadList();
		PageInfo<ArticleHead> info = new PageInfo<ArticleHead>(articles);
		return info;
	}
	public PageInfo<ArticleHead> getPageTopicArticleHead(String topic,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleHead> articles = this.deal.getTopicArticleHeadList(topic);
		PageInfo<ArticleHead> info = new PageInfo<ArticleHead>(articles);
		return info;
	}
	public PageInfo<ArticleHead> getPageUserTopicArticleHead(int authorId,String topic,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleHead> articles = this.deal.getUserTopicArticleHeadList(topic, authorId);
		PageInfo<ArticleHead> info = new PageInfo<ArticleHead>(articles);
		return info;
	}
	public PageInfo<ArticleHead> getUserPageArticleHead(int userId,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleHead> articles = this.deal.getUserArticleHeadList(userId);
		PageInfo<ArticleHead> info = new PageInfo<ArticleHead>(articles);
		return info;
	}
	public PageInfo<Article> getLikeArticle(int pageNum, int pageSize, String liketitle) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getLikeTitle(liketitle);
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}

	public PageInfo<Article> getSubjectArticle(int pageNum, int pageSize, String subject) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getLastArticlesBySubject(subject);
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}

	public PageInfo<Article> getBySubjectAuthor(int pageNum, int pageSize, String author, String subject) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getBySubjectAuthor(author, subject);
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}
	public PageInfo<Article> getBySubjectAuthorId(int pageNum, int pageSize, int authorId, String subject) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getBySubjectAuthorId(authorId, subject);
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}

	public List<Article> getAllArticles() {
		return this.deal.getAllArticles();
	}

	public PageInfo<Article> getPageArticleByUser(int pageNum, int pageSize, String username) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articles = this.deal.getArticleByUser(username);
		PageInfo<Article> info = new PageInfo<Article>(articles);
		return info;
	}
	public PageInfo<ArticleHead> searchByTitle(int pageNum, int pageSize, String keyWord) {
		PageHelper.startPage(pageNum, pageSize);
		String keyLike = "%"+keyWord+"%";
		List<ArticleHead> articles = this.deal.searchList(keyLike);
		PageInfo<ArticleHead> info = new PageInfo<ArticleHead>(articles);
		return info;
	}

	public List<Article> getLast3() {
		return this.deal.getLast3articles();
	}

	public void save(Article a) {
		this.deal.save(a);
	}

	public int saveAndReturn(Article article) {
		this.deal.saveAndReturn(article);
		return article.getId();
	}

	public void deleteArticle(int id) {
		this.deal.deleteArticleById(id);
	}

	public Article getNextBySubject(String subject, int id) {
		return this.deal.getNextBySubject(subject, id);
	}

	public Article getPreviousBySubject(String subject, int id) {
		return this.deal.getPreviousBySubject(subject, id);
	}

	public List<Article> getHotArticles() {
		return this.deal.getHot();
	}

	public List<Article> getUserHotArticles(int userid) {
		return this.deal.getUserHot(userid);
	}

	public Article getLocal(String author, String time, String title) {
		return this.deal.getLocal(author, time, title);
	}

	public int getStarNum(int articleId){
		return deal.getStarNum(articleId);
	}
}
