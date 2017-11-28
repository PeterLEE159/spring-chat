package com.sample.chat.dao;

import java.util.List;

import com.sample.chat.model.User;

public interface UserDao {

	User getUserById(String userid);
	List<User> getUsers();
}
