package com.example.API.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class ThongKe {
	@Id
	private String tensanpham;
	private int tongsoluong;
}
