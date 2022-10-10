package com.example.API.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DonHangRequest {
	private String email;
	private List<CTDonHangCTO> listct;
}