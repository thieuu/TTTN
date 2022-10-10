package com.example.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API.entity.LoaiSanPham;
import com.example.API.repository.LoaiRepository;

@Service
public class LoaiService {
	@Autowired
	private LoaiRepository repo;
	
	public List<LoaiSanPham> listAll(){
		return repo.findAll();
	}
	
	public LoaiSanPham get(Integer maLoai) {
		return repo.findById(maLoai).orElse(null);
	}
	
	public void save(LoaiSanPham maLoai) {
		repo.save(maLoai);
	}
	
	public void delete(Integer maLoai) {;
		repo.deleteById(maLoai);;
	}
	
	public LoaiSanPham findByTenLoai(String tenLoai) {
		return repo.findByTenLoai(tenLoai);
	}
}
