package com.wilsonpedro.parking.dtos;

import com.wilsonpedro.parking.enums.UserRole;

public class RegistroDTO {

	private String login;
	private String password;
	private UserRole role;
	
	public RegistroDTO(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
