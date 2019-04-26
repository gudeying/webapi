package cn.web.service;

import cn.web.model.Application;
import cn.web.sqldeal.AppKeyMapper;

public class AppKeyService {
	private AppKeyMapper mapper;

	/**
	 * 
	 * @param appKey
	 * @param appSecret
	 * @return
	 */
	public boolean check(String appKey, String appSecret) {
		/**
		 * controller端调用， 两个值都不会是null
		 */
		Application application = mapper.getAppByAppKey(appKey);
		if (application == null) {
			return false;
		}
		return application.getAppSecret().equals(appSecret);
	}

}
