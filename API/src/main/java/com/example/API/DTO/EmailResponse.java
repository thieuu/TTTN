package com.example.API.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EmailResponse {
	private String to;
	private String subject;
	private String message;
}
