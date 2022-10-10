package com.example.API.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.DTO.PhieuNhapDTO;
import com.example.API.repository.PhieuNhapRepo;

@Service @Transactional 
public class PhieuNhapService {
	@Autowired
	private PhieuNhapRepo repo;
	
	public List<PhieuNhapDTO> listPhieuNhap(){
		List<PhieuNhapDTO> list = new ArrayList<>();
		for(int i=0;i<repo.findAll().size();i++) {
			PhieuNhapDTO dto = new PhieuNhapDTO();
			dto.setMaphieunhap(repo.findAll().get(i).getMaphieunhap());
			dto.setNgaynhap(repo.findAll().get(i).getNgaynhap());
			dto.setSdt(repo.findAll().get(i).getNguoidung().getSdt());
			list.add(dto);
		}
		return list;
	}
	
//	public int getmaphieunhap(Date ngaynhap,String sdt) {
//		return repo.getmaphieunhap(ngaynhap, sdt);
//	}
	
}
