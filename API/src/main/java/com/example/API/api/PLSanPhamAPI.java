package com.example.API.api;

import java.util.List;

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

import com.example.API.DTO.Status;
import com.example.API.entity.HangSanXuat;
import com.example.API.entity.LoaiSanPham;
import com.example.API.repository.HangSXRepository;
import com.example.API.repository.LoaiRepository;
import com.example.API.service.HangSXService;
import com.example.API.service.LoaiService;

@CrossOrigin
@RestController
public class PLSanPhamAPI {
	
	@Autowired
	private HangSXService hangService;
	
	@Autowired
	private LoaiService loaiService;
	
	@Autowired
	private HangSXRepository hangRepo;
	
	@Autowired
	private LoaiRepository loaiRepo;
	
	@GetMapping("/hang")
	public List<HangSanXuat> listHang(){
		return hangService.listAll();
	}
	
	@GetMapping("/loai")
	public List<LoaiSanPham> listLoai(){
		return loaiService.listAll();
	}
	
	@GetMapping("/hang/{maHangSX}")
	public ResponseEntity<HangSanXuat> getHang(@PathVariable(name = "maHangSX") Integer id) {
			HangSanXuat hangSanXuat = hangService.get(id);
			if(hangSanXuat != null) {
				return new ResponseEntity<HangSanXuat>(hangSanXuat,HttpStatus.OK);
			} else return new ResponseEntity<HangSanXuat>(HttpStatus.NOT_FOUND);

	}
	
	@GetMapping("/loai/{maLoai}")
	public ResponseEntity<LoaiSanPham> getLoai(@PathVariable(name = "maLoai") Integer id) {
			LoaiSanPham loai = loaiService.get(id);
			if(loai != null) {
				return new ResponseEntity<LoaiSanPham>(loai,HttpStatus.OK);
			} else return new ResponseEntity<LoaiSanPham>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/hang")
	public ResponseEntity<?> themHang(@RequestParam String tenhang) {
		HangSanXuat exHang = hangService.findByTenHang(tenhang);
		if(exHang == null) {
			HangSanXuat hangSanXuat = new HangSanXuat();
			hangSanXuat.setTenhang(tenhang);
			hangService.save(hangSanXuat);
			return new ResponseEntity<Status>(new Status(1,"Thành công"),HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Tên hãng đã tồn tại"),HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/loai")
	public ResponseEntity<?> themLoai(@RequestParam String tenloai) {
		LoaiSanPham exLoai = loaiService.findByTenLoai(tenloai);
		if(exLoai == null) {
			LoaiSanPham loaiSanPham = new LoaiSanPham();
			loaiSanPham.setTenloai(tenloai);
			loaiService.save(loaiSanPham);
			return new ResponseEntity<Status>(new Status(1,"Thành công"),HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Tên loại đã tồn tại"),HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/hang/{mahang}")
	public ResponseEntity<?> updateHang (@PathVariable Integer mahang, @RequestParam String tenhang) {
			HangSanXuat exHang = hangService.findByTenHang(tenhang);
			if(exHang == null) {
				HangSanXuat hangSanXuat = hangRepo.getReferenceById(mahang);
				hangSanXuat.setTenhang(tenhang);
				hangService.save(hangSanXuat);
				return new ResponseEntity<Status>(new Status(1,"Thành công"),HttpStatus.OK);
			} else return new ResponseEntity<Status>(new Status(0,"Tên loại đã tồn tại"),HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/loai/{maloai}")
	public ResponseEntity<?> updateLoai(@PathVariable Integer maloai,@RequestParam String tenloai) {
			LoaiSanPham exLoai = loaiService.findByTenLoai(tenloai);
			if(exLoai == null) {
				LoaiSanPham loaiSanPham = loaiRepo.getReferenceById(maloai);
				loaiSanPham.setTenloai(tenloai);
				loaiService.save(loaiSanPham);
				return new ResponseEntity<Status>(new Status(1,"Thành công"),HttpStatus.OK);
			} else return new ResponseEntity<Status>(new Status(0,"Tên loại đã tồn tại"),HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/hang/{maHangSX}")
	public ResponseEntity<?> deleteKieu(@PathVariable Integer maHangSX){		
		HangSanXuat exHang = hangService.get(maHangSX);
		if(exHang != null) {
			hangService.delete(maHangSX);
			return new ResponseEntity<Status>(new Status(1,"Xóa thành công"),HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Không thể xóa"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/loai/{maLoai}")
	public ResponseEntity<?> deleteLoai(@PathVariable Integer maLoai){		
		LoaiSanPham exLoai = loaiService.get(maLoai);
		if(exLoai != null) {
			loaiService.delete(maLoai);
			return new ResponseEntity<Status>(new Status(1,"Xóa thành công"),HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Không thể xóa"),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
