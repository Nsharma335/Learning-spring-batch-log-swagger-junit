package com.example.firstsample.web;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstsample.domain.User;
import com.example.firstsample.repo.UserRepository;
import com.example.firstsample.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class HomeController {

	private UserService userService;
	private UserRepository userRepository;
	private static final Logger LOGEER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public HomeController(UserService userService, UserRepository userRepository) {
		System.out.println("in constructor...");
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@PostMapping
	public void createNew(@RequestBody User user) throws NoSuchElementException {
		LOGEER.info("Post createNew user ", user.getId());
		userService.addUser(user);

	}

	@GetMapping
	public List<User> lookupAllUsers() {
		LOGEER.debug("lookupAllUsers ");
		return userRepository.findAll();
	}

	@GetMapping("/hello")
	public String sayHello() {
		LOGEER.debug("get hello user ");
		System.out.println("in hello...");
		return "Hey";
	}

	private User verifyUser(int userId) throws NoSuchElementException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User does not exist " + userId));
	}

}
