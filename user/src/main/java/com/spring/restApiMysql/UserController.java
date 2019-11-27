package com.spring.restApiMysql;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		return token;
	}
	
	@PostMapping("/login")
	public Object login(@RequestBody Map<String, String> body) throws Exception {
		String username = body.get("username");
		String password = body.get("password");
		List<User> users = userRepository.findAll();
		for(User user: users){
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				int id = user.getId();
				String token = serviceDiscoveryClient.genTokenLogin(id, username, password);
				return token;
			}
		}
		return new ResponseEntity("incorrect username or password", HttpStatus.FORBIDDEN);
	}

	@PostMapping("/logout")
	public String logout(@RequestHeader("Authorization") String value) throws Exception {
		return serviceDiscoveryClient.logout("Authorization", value);
	}
	
	@GetMapping("/user/me")
	public Optional<User> auth(@RequestHeader("Authorization") String value) throws Exception  {
		int userId = serviceDiscoveryClient.getUserId("Authorization", value);
		return userRepository.findById(userId);
	}

	@PostMapping("/user/update")
	public Object updateUser(@RequestHeader("Authorization") String value, @RequestBody Map<String, Object> body) throws Exception {
		int userId = serviceDiscoveryClient.getUserId("Authorization", value);
		User this_user = userRepository.findById(userId).orElse(null);
		if(body.get("username") != null) {
			this_user.setUsername((String) body.get("username"));
			serviceDiscoveryClient.updateUser("Authorization", value, this_user.getUsername(), this_user.getPassword());
		}
		if(body.get("password") != null) {
			this_user.setPassword((String) body.get("password"));
			serviceDiscoveryClient.updateUser("Authorization", value, this_user.getUsername(), this_user.getPassword());
		}
		if(body.get("firstName") != null)
			this_user.setFirstName((String) body.get("firstName"));
		if(body.get("lastName") != null)
			this_user.setLastName((String) body.get("lastName"));
		if(body.get("middleName") != null)
			this_user.setMiddleName((String) body.get("middleName"));
		if(body.get("tel") != null)
			this_user.setTel((List<String>) body.get("tel"));
		if(body.get("email") != null)
			this_user.setEmail((String) body.get("email"));
		return userRepository.save(this_user);
	}
}
