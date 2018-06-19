package com.tsb.study.shiro.web03_spring.service.impl;

import com.tsb.study.shiro.web03_spring.dao.UserDao;
import com.tsb.study.shiro.web03_spring.entity.User;
import com.tsb.study.shiro.web03_spring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service("userService")
public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDao();
	
	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public Set<String> getRoles(String userName) {
		return userDao.getRoles(userName);
	}

	public Set<String> getPermissions(String userName) {
		return userDao.getPermissions(userName);
	}

}
