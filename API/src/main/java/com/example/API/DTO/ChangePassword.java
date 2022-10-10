package com.example.API.DTO;

public class ChangePassword extends LoginRequest{
	String matKhauMoi;

	public String getMatKhauMoi() {
		return matKhauMoi;
	}

	public void setMatKhauMoi(String matKhauMoi) {
		this.matKhauMoi = matKhauMoi;
	}

	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePassword(String tendn, String matkhau) {
		super(tendn, matkhau);
		// TODO Auto-generated constructor stub
	}

	public ChangePassword(String matKhauMoi) {
		super();
		this.matKhauMoi = matKhauMoi;
	}
}
