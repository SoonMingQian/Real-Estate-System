package com.example.backend.payload.response;

import java.util.List;

public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	public JwtResponse(String accessToken, Long id, String firstName, String lastName, String email, List<String> roles) {
		super();
		this.token = accessToken;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}
	
	
	public String getAccessToken() {
		return token;
	}
	
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getTokenType() {
		return type;
	}
	public void setTokeType(String tokenType) {
		this.type = tokenType;
	}
	
	
}
