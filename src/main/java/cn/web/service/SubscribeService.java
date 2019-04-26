package cn.web.service;

import cn.web.model.Article;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class SubscribeService {
	@Autowired
	private SetOperations setOperations;

	public void subscribe(int currentUser, int targetUser) {
		String key = "subscribeList" + targetUser;
		this.setOperations.add(key, new Object[] { Integer.valueOf(currentUser) });

		this.setOperations.getOperations().expire(key, 365L, TimeUnit.DAYS);
	}

	public void unSubscribe(int currentUser, int targetUser) {
		this.setOperations.remove("subscribeList" + targetUser, new Object[] { Integer.valueOf(currentUser) });
	}

	public Boolean isSubscribe(int targetUser, int currentUser) {
		return this.setOperations.isMember("subscribeList" + targetUser, Integer.valueOf(currentUser));
	}

	public Set getSubscribedUser(int currentUser) {
		Set sets = this.setOperations.members("subscribeList" + currentUser);
		return sets;
	}

	public Set<Article> getToReadList(int userId) {
		Set<Article> sets = null;
		try {
			sets = this.setOperations.members("toReadList" + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	public int toReadeSize(int userId) {
		return this.setOperations.members("toReadList" + userId).size();
	}

	public void clearToRead(int userId) {
		this.setOperations.getOperations().delete("toReadList" + userId);
	}
}
