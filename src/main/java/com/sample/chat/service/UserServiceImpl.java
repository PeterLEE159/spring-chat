package com.sample.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.chat.dao.UserDao;
import com.sample.chat.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User getUser(String userid) {
		return userDao.getUserById(userid);
	}
	
	@Override
	public List<User> getAllUser() {
		return userDao.getUsers();
	}
}
