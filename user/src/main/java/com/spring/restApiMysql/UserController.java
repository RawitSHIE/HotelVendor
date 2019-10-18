package com.spring.restApiMysql;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/alluser")
	public List<User> index(){
		return userRepository.findAll();
	}
	
	@GetMapping("user/{id}")
	public User show(@PathVariable String id) {
		int userId = Integer.parseInt(id);
		return userRepository.findById(userId).orElse(null);
	}

	@PostMapping("/user")
	public User create(@RequestBody User user) {
		return userRepository.save(user);
	}
}
