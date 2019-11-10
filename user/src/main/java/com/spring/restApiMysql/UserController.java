package com.spring.restApiMysql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@EnableDiscoveryClient
@EnableJpaAuditing
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
	public Optional<User> auth(@RequestHeader("Authorization") String value) throws Exception  {
		int userId = serviceDiscoveryClient.getUserId("Authorization", value);
		return userRepository.findById(userId);
	}

	@PostMapping("/user/update")
	public Object updateUser(@RequestHeader("Authorization") String value, @RequestBody User user) throws Exception {
		int userId = serviceDiscoveryClient.getUserId("Authorization", value);
		User this_user = userRepository.findById(userId).orElse(null);
		if(user.getUsername()!=null) {
			this_user.setUsername(user.getUsername());
			serviceDiscoveryClient.updateUser("Authorization", value, this_user.getUsername(), this_user.getPassword());
		}
		if(user.getPassword()!=null) {
			this_user.setPassword(user.getPassword());
			serviceDiscoveryClient.updateUser("Authorization", value, this_user.getUsername(), this_user.getPassword());
		}
		if(user.getFirstName()!=null)
			this_user.setFirstName(user.getFirstName());
		if(user.getLastName()!=null)
			this_user.setLastName(user.getLastName());
		if(user.getMiddleName()!=null)
			this_user.setMiddleName(user.getMiddleName());
		if(user.getTel()!=null)
			this_user.setTel(user.getTel());
		if(user.getEmail()!=null)
			this_user.setEmail(user.getEmail());
		return userRepository.save(this_user);
	}
}
