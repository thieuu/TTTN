package com.example.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API.repository.HangSXRepository;
import com.example.API.entity.HangSanXuat;

@Service
public class HangSXService {
	
	@Autowired
	private HangSXRepository repo;
	
	public List<HangSanXuat> listAll(){
		return repo.findAll();
	}
	
	public HangSanXuat get(Integer maHangSX) {
		return repo.findById(maHangSX).orElse(null);
	}
	
	public void save(HangSanXuat maHangSX) {
		repo.save(maHangSX);
	}
	
	public void delete(Integer maHangSX) {;
		repo.deleteById(maHangSX);;
	}
	
	public HangSanXuat findByTenHang(String tenhang) {
		return repo.findByTenHang(tenhang);
	}
}
