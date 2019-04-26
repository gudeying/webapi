package cn.web.service;

import cn.web.model.User;
import cn.web.sqldeal.UpdateMapper;
import cn.web.sqldeal.UserMappi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class UserService {
	@Autowired
	public UserMappi usermappi;
	@Autowired
	private UpdateMapper updateMapper;

	public User getUserByName(String name) {
		return this.usermappi.getUserByName(name);
	}

	public String getPaswByName(String name) {
		return this.usermappi.getPaswByName(name);
	}

	public User getUserById(int id) {
		return this.usermappi.getUserById(id);
	}

	public void deleteUserById(int id) {
		this.usermappi.deleteUserById(id);
	}

	public User getUserByOpenid(String openid) {
		return this.usermappi.getUserByOpenid(openid);
	}

	public User getUserByMailBox(String mail) {
		return this.usermappi.getUserByMailBox(mail);
	}

	public void save(User user) {
		this.usermappi.save(user);
	}

	@Transactional
	public int UpdateUser(User user) {
		return this.updateMapper.updateuser(user);
	}

	public void updateUserLevel(User user) {
		this.usermappi.updateUserLevel(user);
	}
}
