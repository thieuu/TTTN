package com.example.API.api;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.DTO.PhieuNhapDTO;
import com.example.API.DTO.PhieuNhapRequest;
import com.example.API.DTO.Status;
import com.example.API.entity.CTPhieuNhap;
import com.example.API.entity.CTPhieuNhapID;
import com.example.API.entity.NguoiDung;
import com.example.API.entity.PhieuNhap;
import com.example.API.entity.SanPham;
import com.example.API.repository.CTPhieuNhapRepo;
import com.example.API.repository.NguoiDungRepo;
import com.example.API.repository.PhieuNhapRepo;
import com.example.API.repository.SanPhamRepository;
import com.example.API.service.PhieuNhapService;

@CrossOrigin
@RestController
public class PhieuNhapAPI {
	@Autowired
	private PhieuNhapService service;

	@Autowired
	private PhieuNhapRepo repo;

	@Autowired
	private NguoiDungRepo ngDungRepo;

	@Autowired
	private CTPhieuNhapRepo ctPhieuNhapRepo;

	@GetMapping("/phieunhap")
	private List<PhieuNhapDTO> getAll() {
		return service.listPhieuNhap();
	}

	@GetMapping("/phieunhap/all")
	private List<PhieuNhap> test() {
		return repo.findAll();
	}

	@PostMapping("/phieunhap")
	private ResponseEntity<Status> postsp(@RequestBody PhieuNhapRequest pn) {
		try {			
	        LocalDateTime now = LocalDateTime.now();
			PhieuNhap phieuNhap = new PhieuNhap();
			phieuNhap.setNgaynhap(java.sql.Timestamp.valueOf(now));
			phieuNhap.setNguoidung(ngDungRepo.findOneBySdt(pn.getSdt()));
			repo.save(phieuNhap);
					
			PhieuNhap phieuNhap2 = new PhieuNhap();
			phieuNhap2  = repo.getphieunhap(java.sql.Timestamp.valueOf(now), pn.getSdt());
			
			for (int i = 0; i < pn.getListct().size(); i++) {
				ctPhieuNhapRepo.insertCTPN(pn.getListct().get(i).getGianhap(),
										   pn.getListct().get(i).getSoluongnhap(),
										   phieuNhap2.getMaphieunhap(),
										   pn.getListct().get(i).getMasanpham());
			}			
			return new ResponseEntity<Status>(new Status(1, "Thêm thành công"), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Status>(new Status(0, "Thêm thất bại"), HttpStatus.EXPECTATION_FAILED);
		}
	}
}
