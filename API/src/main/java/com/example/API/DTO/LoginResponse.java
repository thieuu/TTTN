package com.example.API.DTO;

public class LoginResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private String role;
	
	public LoginResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public LoginResponse(String accessToken, String role) {
		super();
		this.accessToken = accessToken;
		this.role = role;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
