package com.example.API.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class KhachHang {
	private String sdt;
	private String hoten;
	private String diachi;
	private String email;
}
