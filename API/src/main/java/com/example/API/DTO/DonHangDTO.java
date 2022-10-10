package com.example.API.DTO;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DonHangDTO {
	private int madonhang;
	@JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
	private Date ngaydathang;
	private String sdt;
	private List<CTDonHangCTO> ctdonhang;
	private String trangthai;
}
