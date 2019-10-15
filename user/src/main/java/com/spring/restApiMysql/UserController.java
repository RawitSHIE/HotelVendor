package com.spring.restApiMysql;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/user")
	public List<User> index(){
		return userRepository.findAll();
	}
	
	@GetMapping("user/{id}")
	public User show(@PathVariable String id) {
		int userId = Integer.parseInt(id);
		return userRepository.findById(userId).orElse(null);
	}
	
	@PostMapping("/user")
	public User create(@RequestBody Map<String, String> body) {
		String firstname = body.get("firstname");
		String middlename = body.get("middlename");
		String lastname = body.get("lastname");
		String tel = body.get("tel");
		String email = body.get("email");
		return userRepository.save(new User(firstname, middlename, lastname, tel, email));
	}
}
