package com.sample.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sample.chat.model.User;

@Service
public interface UserService {

	User getUser(String userid);
	List<User> getAllUser();
}
