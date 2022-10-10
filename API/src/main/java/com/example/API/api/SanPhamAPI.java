package com.example.API.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.DTO.LoaiAndHang;
import com.example.API.DTO.SanPhamDTO;
import com.example.API.DTO.Status;
import com.example.API.DTO.ThongKe;
import com.example.API.entity.SanPham;
import com.example.API.repository.SanPhamRepository;
import com.example.API.repository.ThongKeRepo;
import com.example.API.service.HangSXService;
import com.example.API.service.LoaiService;
import com.example.API.service.SanPhamService;

@CrossOrigin
@RestController
public class SanPhamAPI {
	@Autowired
	private SanPhamService service;
	
	@Autowired
	private SanPhamRepository repo;
	
	@Autowired
	private HangSXService hangservice;
	
	@Autowired
	private LoaiService loaiservice;
	
	@Autowired
	private ThongKeRepo thongKeRepo;
	
	@GetMapping(value = {"/","/sanpham"})
	public List<SanPhamDTO> listEnable(){
		return service.listEnable();
	}
	
	@GetMapping(value = {"/giaban"})
	public List<SanPham> listSanPhamTheoGia(@RequestParam Integer giaban){
		return repo.listsanpham(giaban);
	}
	
	@GetMapping("/sanphamdaxoa")
	public List<SanPhamDTO> listDisable(){
		return service.listDisable();
	}
	
	@GetMapping("/sanpham/{maSP}")
	public ResponseEntity<?> get(@PathVariable Integer maSP) {
		SanPham sanpham = service.get(maSP);
		if(sanpham != null) {
			return new ResponseEntity<SanPham>(sanpham,HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Khong tim thay san pham"),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/sanpham/ten")
	public List<SanPhamDTO> getTenSanPham(@RequestParam String ten){
		return service.listByTen(ten);
	}
	
	@GetMapping("/sanpham/hang-{maHangSX}")
	public List<SanPham> listKieu(@PathVariable Integer maHangSX){
		return service.lisHangSX(maHangSX);
	}
	
	@GetMapping("/sanpham/loai-{maLoai}")
	public List<SanPham> listLoai(@PathVariable Integer maLoai){
		return service.listLoai(maLoai);
	}
	
	@GetMapping("/sanpham/loai-hang")
	public List<SanPham> listLoaiandKieu(@RequestBody LoaiAndHang id){
		return service.listLoaiAndHangSX(id.getMaLoai(), id.getMaHangSX());
	}
	
	@PostMapping("/sanpham")
	public ResponseEntity<Status> add(@RequestBody SanPhamDTO sanpham) {
		try {
			SanPham sanPhamNew = new SanPham();
			sanPhamNew.setTensanpham(sanpham.getTensanpham());
			sanPhamNew.setMota(sanpham.getMota());
			sanPhamNew.setGiaban(sanpham.getGiaban());
			//sanPhamNew.setAnhSP(sanpham.getAnhSP());
			sanPhamNew.setAnhsanpham(sanpham.anhsanpham);
			sanPhamNew.setLoaisanpham(loaiservice.findByTenLoai(sanpham.getTenloai()));
			sanPhamNew.setHangsanxuat(hangservice.findByTenHang(sanpham.getTenhang()));
			sanPhamNew.setTrangthai(true);
			sanPhamNew.setSoluongton(0);
			service.save(sanPhamNew);
			return new ResponseEntity<Status>(new Status(1,"Them thanh cong"),HttpStatus.OK);
		}catch(NoSuchElementException e){
			return new ResponseEntity<Status>(new Status(0,"Them that bai"),HttpStatus.OK);
		}	
	}
	
	@PutMapping("/sanpham/{id}")
	public ResponseEntity<?> update(@RequestBody SanPhamDTO sanpham,@PathVariable Integer id) {
		try {
			SanPham exSanPhamEntity = service.get(id);
			if(exSanPhamEntity != null) {
				exSanPhamEntity.setTensanpham(sanpham.getTensanpham());
				exSanPhamEntity.setMota(sanpham.getMota());
				exSanPhamEntity.setGiaban(sanpham.getGiaban());
				//sanPhamNew.setAnhSP(sanpham.getAnhSP());
				exSanPhamEntity.setAnhsanpham(sanpham.anhsanpham);
				exSanPhamEntity.setLoaisanpham(loaiservice.findByTenLoai(sanpham.getTenloai()));
				exSanPhamEntity.setHangsanxuat(hangservice.findByTenHang(sanpham.getTenhang()));
				service.save(exSanPhamEntity);
				return new ResponseEntity<Status>(new Status(1,"Sua thanh cong"),HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Status>(new Status(0,"Sua that bai"),HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/xoasanpham/{maSP}")
	public ResponseEntity<?> delete(@PathVariable Integer maSP){
		try {
			SanPham sanPham = service.get(maSP);
			if(sanPham != null) {
				try{
					service.delete(maSP);
				}catch(Exception e) {
					sanPham.setTrangthai(false);
					service.save(sanPham);
				}
			}
			return new ResponseEntity<Status>(new Status(1,"Xoa thanh cong"),HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Status>(new Status(0,"Xoa that bai"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/hienthi")
	public ResponseEntity<Status> hienthi(@RequestParam int masanpham) {
		try {
			SanPham sanPham = service.get(masanpham);
			if(sanPham != null) {
				sanPham.setTrangthai(true);
			}
			service.save(sanPham);
			return new ResponseEntity<Status>(new Status(1,"Thanh cong"),HttpStatus.OK);
		}catch(NoSuchElementException e){
			return new ResponseEntity<Status>(new Status(0," that bai"),HttpStatus.OK);
		}	
	} 
	
	@GetMapping("/thongke/{thang}-{nam}")
	public List<ThongKe> listThongKe(@PathVariable(name = "thang") int thang,@PathVariable(name = "nam") int nam){
		return thongKeRepo.listThongKe(thang, nam);
	}
}
