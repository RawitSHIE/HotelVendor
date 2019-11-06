package com.spring.restApiMysql;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;


@RestController
@EnableDiscoveryClient

public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ServiceDiscoveryClient serviceDiscoveryClient;

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
	public String create(@RequestBody User user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();
		userRepository.save(user);
		int id = user.getId();
		String token = serviceDiscoveryClient.genTokenForNewUser(id, username, password);
		return user.toString() + "\ntoken " + token;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> body) throws Exception {
		String username = body.get("username");
		String password = body.get("password");
		List<User> users = userRepository.findAll();
		for(User user: users){
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				int id = user.getId();
				String token = serviceDiscoveryClient.genTokenLogin(id, username, password);
				return "you're in. " + user.getFirstName() + " " + user.getLastName() + " authen : " + token;
			}
		}
		return "incorrect username or password";
	}
	
	@GetMapping("/user/me")
	public int auth(@RequestHeader("Authorization") String value) throws Exception  {
		int userId = serviceDiscoveryClient.getUserId("Authorization", value);
		return userId;
	}
}
