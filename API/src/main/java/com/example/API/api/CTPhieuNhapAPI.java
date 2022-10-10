package com.example.API.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.DTO.CTDonHang2;
import com.example.API.service.CTPhieuNhapService;

@CrossOrigin
@RestController
public class CTPhieuNhapAPI {
	@Autowired
	private CTPhieuNhapService service;
	
	@GetMapping("/ctphieunhap/{maphieunhap}")
	public List<CTDonHang2> listCTDH(@PathVariable Integer maphieunhap){
		return service.findByMaphieunhap(maphieunhap);
	}
}
