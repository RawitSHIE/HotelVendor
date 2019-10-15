	package com.spring.restApiMysql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "User") if your table name is different from the class name.
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
//	name
	private String firstname;
	private String middlename;
	private String lastname;
	
//	contactInfo
	private String tel;
	private String email;
	
	public User() {}
	
	public User(String firstname, String middlename, String lastname, String tel, String email) {
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.tel = tel;
		this.email = email;
	}
	public User(int id, String firstname, String middlename, String lastname, String tel, String email) {
		this.id = id;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.tel = tel;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddleName(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
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
		return "User {id=" + id + ", firstname=" + firstname + ", middlename=" + middlename + ", lastname=" + lastname
				+ ", tel=" + tel + ", email=" + email + "}";
	}
}
