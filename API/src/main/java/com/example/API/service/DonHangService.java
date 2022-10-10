package com.example.API.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.DTO.CTDonHangCTO;
import com.example.API.DTO.DonHang2;
import com.example.API.DTO.DonHangDTO;
import com.example.API.entity.DonHang;
import com.example.API.repository.DonHangRepo;

@Service @Transactional
public class DonHangService {
	@Autowired
	private DonHangRepo repo;
	
	@Autowired
	private CTDonHangService service;
	
	public List<DonHangDTO> listAll(){
		List<DonHangDTO> list = new ArrayList<>();
		for(int i = 0;i < repo.findAll().size();i++) {
			list.add(covertDonHangToDTO(repo.findAll().get(i)));
		}
		return list;
	}
	
	public void save(DonHang donhang) {		
		repo.save(donhang);
	}
	
	public List<DonHang2> findAll(){
		List<DonHang2> donHang2 = new ArrayList<>();
		for(int i=0;i<repo.findAll().size();i++) {
			DonHang2 donHang = new DonHang2();
			donHang.setMadonhang(repo.findAll().get(i).getMadonhang());
			donHang.setNgaydathang(repo.findAll().get(i).getNgaydathang());
			donHang.setSdt(repo.findAll().get(i).getNguoidung().getSdt());
			donHang.setTrangthai(repo.findAll().get(i).getTrangthai());
			donHang.setEmail(repo.findAll().get(i).getNguoidung().getEmail());
			donHang.setDiachi(repo.findAll().get(i).getNguoidung().getDiachi());
			donHang.setHoten(repo.findAll().get(i).getNguoidung().getHoten());
			donHang2.add(donHang);
		}
		return donHang2;
	}
	
	public List<DonHang2> findBySdt(String sdt){
		List<DonHang2> donHang2 = new ArrayList<>();
		for(int i=0;i<repo.findBySdt(sdt).size();i++) {
			DonHang2 donHang = new DonHang2();
			donHang.setMadonhang(repo.findBySdt(sdt).get(i).getMadonhang());
			donHang.setNgaydathang(repo.findBySdt(sdt).get(i).getNgaydathang());
			donHang.setSdt(repo.findBySdt(sdt).get(i).getNguoidung().getSdt());
			donHang.setTrangthai(repo.findBySdt(sdt).get(i).getTrangthai());
			donHang.setEmail(repo.findBySdt(sdt).get(i).getNguoidung().getEmail());
			donHang.setDiachi(repo.findBySdt(sdt).get(i).getNguoidung().getDiachi());
			donHang.setHoten(repo.findBySdt(sdt).get(i).getNguoidung().getHoten());
			donHang2.add(donHang);
		}
		return donHang2;
	}
	
	private DonHangDTO covertDonHangToDTO(DonHang donHang) {
		DonHangDTO dhdto = new DonHangDTO();
		dhdto.setMadonhang(donHang.getMadonhang());
		dhdto.setNgaydathang(donHang.getNgaydathang());
		dhdto.setSdt(donHang.getNguoidung().getSdt());
		List<CTDonHangCTO> ctDonHangCTOs = new ArrayList<>();
		for(int i = 0;i<donHang.getCtdonhang().size();i++) {
			ctDonHangCTOs.add(service.covertCTDonHangToDTO(donHang.getCtdonhang().get(i)));
		}
		dhdto.setCtdonhang(ctDonHangCTOs);
		dhdto.setTrangthai(donHang.getTrangthai());
		return dhdto;
	}
}
