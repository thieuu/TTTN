package com.example.API.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PhieuNhapDTO {
	private int maphieunhap;
	@JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
	private Date ngaynhap;
	private String sdt;
}
