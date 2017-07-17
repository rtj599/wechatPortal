package com.rtj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rtj.dao.IUserDao;
import com.rtj.dto.User;
import com.rtj.service.IUserService;

@Service("userService")
public class UserServiceImpl  implements IUserService{
	
	@Resource
	private IUserDao userDao;
	
	public User getUserById(int userId) {
		return userDao.queryByPrimaryKey(userId);
	}

	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	public void deleteUser(int userId) {
		userDao.deleteByPrimaryKey(userId);
	}

	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	public void updateUser(User user) {
		userDao.updateByPrimaryKey(user);
		
	}
	

}
