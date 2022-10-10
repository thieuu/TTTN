package com.example.API.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.API.DTO.CTDonHang2;
import com.example.API.repository.CTPhieuNhapRepo;

@Service @Transactional
public class CTPhieuNhapService {
	@Autowired
	private CTPhieuNhapRepo ctrepo;
	
	public List<CTDonHang2> findByMaphieunhap(int maphieunhap){
		List<CTDonHang2> ctDonHang2 = new ArrayList<>();	
		for(int i=0;i<ctrepo.findByMaphieunhap(maphieunhap).size();i++) {
			CTDonHang2 hang2 = new CTDonHang2();
			hang2.setTensanpham(ctrepo.findByMaphieunhap(maphieunhap).get(i).getSanpham().getTensanpham());
			hang2.setAnhsanpham(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
					.path(ctrepo.findByMaphieunhap(maphieunhap).get(i).getSanpham().getAnhsanpham()).toUriString());
			hang2.setSoluong(ctrepo.findByMaphieunhap(maphieunhap).get(i).getSoLuong());
			hang2.setGiamua(ctrepo.findByMaphieunhap(maphieunhap).get(i).getGianhap());
			ctDonHang2.add(hang2);
		}
		return ctDonHang2;
	}
}
