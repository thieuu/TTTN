package com.example.API.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.API.DTO.CTDonHang2;
import com.example.API.DTO.CTDonHangCTO;
import com.example.API.DTO.DonHang2;
import com.example.API.entity.CTDonHang;
import com.example.API.repository.CTDonHangRepo;

@Service @Transactional 
public class CTDonHangService {
	@Autowired
	private CTDonHangRepo ctrepo;
	
	public List<CTDonHang2> findByMadonhang(int madonhang){
		List<CTDonHang2> ctDonHang2 = new ArrayList<>();	
		for(int i=0;i<ctrepo.findByMadonhang(madonhang).size();i++) {
			CTDonHang2 hang2 = new CTDonHang2();
			hang2.setTensanpham(ctrepo.findByMadonhang(madonhang).get(i).getSanpham().getTensanpham());
			hang2.setAnhsanpham(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
					.path(ctrepo.findByMadonhang(madonhang).get(i).getSanpham().getAnhsanpham()).toUriString());
			hang2.setSoluong(ctrepo.findByMadonhang(madonhang).get(i).getSoluong());
			hang2.setGiamua(ctrepo.findByMadonhang(madonhang).get(i).getGiamua());
			ctDonHang2.add(hang2);
		}
		return ctDonHang2;
	}
	
	public CTDonHangCTO covertCTDonHangToDTO(CTDonHang ctdh) {
		CTDonHangCTO ctDonHangCTO = new CTDonHangCTO();
		ctDonHangCTO.setMasanpham(ctdh.getSanpham().getMasanpham());
		ctDonHangCTO.setGiamua(ctdh.getGiamua());
		ctDonHangCTO.setSoluongmua(ctdh.getSoluong());
		return ctDonHangCTO;
	}
}
