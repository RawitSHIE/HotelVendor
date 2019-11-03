package com.spring.restApiMysql;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull(message = "can't be empty")
	@Column(unique=true)
	private String username;
	
	@NotNull(message = "can't be empty")
	private String password;
	
	@NotNull(message = "can't be empty")
	private String firstName;

	private String middleName;

	@NotNull(message = "can't be empty")
	private String lastName;

	@ElementCollection
	@CollectionTable(
			name = "user_tel",
			joinColumns = @JoinColumn(name = "user_id")
	)
	@Column(name = "tel")
	private Set<String> tel = new HashSet<String>();

	@NotNull(message = "can't be empty")
	private String email;
	
	public User() {}
	
	public User(String username, String password, String firstName, String middleName, String lastName, Set<String> tel, String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.tel = tel;
		this.email = email;
	}

	public User(int id, String username, String password, String firstName, String middleName, String lastName, Set<String> tel, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.tel = tel;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<String> getTel() {
		return tel;
	}

	public void setTel(Set<String> tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User {id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", email=" + email + "}";
	}
}
