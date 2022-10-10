package com.example.API.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DonHang2 {
	private int madonhang;
	@JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
	private Date ngaydathang;
	private String sdt;
	private String trangthai;
	private String diachi;
	private String email;
	private String hoten;
}
