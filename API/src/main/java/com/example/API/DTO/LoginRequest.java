package com.example.API.DTO;

public class LoginRequest {
	private String tendn;
	private String matkhau;

	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRequest(String tendn, String matkhau) {
		super();
		this.tendn = tendn;
		this.matkhau = matkhau;
	}

	public String getTendn() {
		return tendn;
	}

	public void setTendn(String tendn) {
		this.tendn = tendn;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
}
