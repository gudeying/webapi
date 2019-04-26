package cn.web.service;

import cn.web.model.Article;
import cn.web.sqldeal.ArticleDeal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Service
public class EnshrineService {
    @Autowired
    private ArticleDeal deal;
    @Autowired
    private SetOperations setOperations;

    public void shoucang(String userId, String articleId) {
        this.setOperations.add(userId, new Object[]{articleId});
        this.setOperations.getOperations().expire(userId, 365L, TimeUnit.DAYS);
        this.deal.shoucang(Integer.parseInt(articleId));
    }

    public boolean isEnshrine(String userId, String articleId) {
        return this.setOperations.isMember(userId, articleId).booleanValue();
    }

    public void cancelEnshrine(String userId, String articleId) {
        this.setOperations.remove(userId, new Object[]{articleId});
        this.deal.quxiao(Integer.parseInt(articleId));
    }

    public List<Article> getEnshrinedArticle(String userId) {
        List<Article> lists = new ArrayList();
        Set set = this.setOperations.members(userId);
        int size = set.size();
        for (Object merber : set) {
            try {
                lists.add(this.deal.getArticleById(Integer.parseInt((String) merber)));
            } catch (NumberFormatException e) {
            }
        }
        return lists;
    }

    public int getCount(String userId) {
        return this.setOperations.members(userId).size();
    }
}
