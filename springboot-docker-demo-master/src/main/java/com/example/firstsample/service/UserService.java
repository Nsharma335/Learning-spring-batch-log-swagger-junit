package com.example.firstsample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firstsample.domain.User;
import com.example.firstsample.repo.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void addUser(User user) {
		userRepository.save(new User(user.getId(), user.getName(), user.getEmail(), user.getPassword()));
	}

}
