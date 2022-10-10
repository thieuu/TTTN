package com.example.API.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.text.DecimalFormat;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.service.DonHangService;
import com.example.API.service.EmailService;
import com.example.API.DTO.DonHang2;
import com.example.API.DTO.DonHangDTO;
import com.example.API.DTO.DonHangRequest;
import com.example.API.DTO.PhieuNhapRequest;
import com.example.API.DTO.Status;
import com.example.API.entity.DonHang;
import com.example.API.entity.PhieuNhap;
import com.example.API.repository.CTDonHangRepo;
import com.example.API.repository.DonHangRepo;
import com.example.API.repository.NguoiDungRepo;

@CrossOrigin
@RestController
public class DonHangAPI {
	@Autowired
	private DonHangService service;
	
	@Autowired
	private DonHangRepo repo;
	
	@Autowired 
	private NguoiDungRepo ngDungRepo;
	
	@Autowired
	private CTDonHangRepo ctDonHangRepo;
	
	@Autowired
	private NguoiDungRepo nguoiDungRepo;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/donhang")
	public List<DonHangDTO> list(){
		return service.listAll();
	}
	
	@GetMapping("/donhang/getall")
	public List<DonHang> listGetALL(){
		return repo.findAll();
	}
	
	@GetMapping("/donhang/sdt/{sdt}")
	public List<DonHang2> findBySdt(@PathVariable String sdt){
		return service.findBySdt(sdt);
	}
	
	@GetMapping("/donhang/all")
	public List<DonHang2> findAll(){
		return service.findAll();
	}
	
	@PutMapping("/donhang/{madonhang}")
	public ResponseEntity<Status> duyetdon(@RequestParam String trangthai,@PathVariable Integer madonhang){
			DonHang donHang = repo.findById(madonhang).orElse(null);
			if(donHang != null) {	
			donHang.setTrangthai(trangthai);
			if(trangthai.equalsIgnoreCase("Đã hủy")) {
				for(int i = 0;i<donHang.getCtdonhang().size();i++) {
					int soluongton = donHang.getCtdonhang().get(i).getSanpham().getSoluongton();
					donHang.getCtdonhang().get(i).getSanpham().setSoluongton(soluongton + donHang.getCtdonhang().get(i).getSoluong());
				}
			}
			
//			if(!donHang.getTrangthai().equals(trangthai)){
//				String to = nguoiDungRepo.findKHBySdt(donHang.getNguoidung().getSdt()).getEmail();
//				String subject = "Hello";
//				String message = "Hello";  
////				DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
////				String tongtien = decimalFormat.format(Integer.parseInt(dh.getTongTien()));
//				if(donHang.getTrangthai().equals("Đang giao")) {
//					 subject = "THÔNG BÁO GIAO HÀNG TỪ WATCHSHOP";
//					 message = "Đơn hàng của bạn đang được giao trong thời gian sớm nhất.Xin cảm ơn!"
//					 		+ "\n Mã đơn hàng: "+ donHang.getMadonhang()
//					 		+ "\n Ngày đặt hàng: "+ donHang.getNgaydathang()
//					 		+ "\n Số điện thoại: "+ donHang.getNguoidung().getSdt();
////					 		+ "\n Tổng tiền: " + tongtien + " Đ";
//				} else if(donHang.getTrangthai().equals("Đã hủy")) {
//					subject = "THÔNG BÁO HỦY ĐƠN HÀNG TỪ WATCHSHOP";
//					message = "Đơn hàng của bạn đã bị hủy.Xin lỗi bạn về sự cố này!"
//							+ "\n Mã đơn hàng: "+ donHang.getMadonhang()
//					 		+ "\n Ngày đặt hàng: "+ donHang.getNgaydathang()
//					 		+ "\n Số điện thoại: "+ donHang.getNguoidung().getSdt();
////					 		+ "\n Tổng tiền: "+ tongtien + " Đ";
//				}else if(donHang.getTrangthai().equals("Đã giao")) {
//					subject = "THÔNG BÁO GIAO THÀNH CÔNG TỪ WATCHSHOP";
//					message = "Đơn hàng của bạn đã được giao thành công.Hãy gửi đánh giá về sản phẩm cho shop nhé!"
//							+ "\n Mã đơn hàng: "+ donHang.getMadonhang()
//					 		+ "\n Ngày đặt hàng: "+ donHang.getNgaydathang()
//					 		+ "\n Số điện thoại: "+ donHang.getNguoidung().getSdt();
////					 		+ "\n Tổng tiền: "+ tongtien+ " Đ";
//				}
//				emailService.sendEmail(subject, message, to);
//			}
			repo.save(donHang);
			return new ResponseEntity<Status>(new Status(1,"Thành công"),HttpStatus.OK);
			} 
			return new ResponseEntity<Status>(new Status(0,"Thất bại"),HttpStatus.OK);
	}
	
	@PostMapping("/donhang")
	private ResponseEntity<Status> postsp(@RequestBody DonHangRequest dh) {
		try {			
	        LocalDateTime now = LocalDateTime.now();
			DonHang donHang = new DonHang();
			donHang.setNgaydathang(java.sql.Timestamp.valueOf(now));
			donHang.setNguoidung(ngDungRepo.findByEmail(dh.getEmail()));
			donHang.setTrangthai("Chờ duyệt");
			repo.save(donHang);
					
			DonHang donHang2 = new DonHang();
			donHang2  = repo.getdonhang(java.sql.Timestamp.valueOf(now), ngDungRepo.findByEmail(dh.getEmail()).getSdt());
			
			for (int i = 0; i < dh.getListct().size(); i++) {
				ctDonHangRepo.insertCTDH(dh.getListct().get(i).getGiamua(),
										   dh.getListct().get(i).getSoluongmua(),
										   donHang2.getMadonhang(),
										   dh.getListct().get(i).getMasanpham());
			}			
			return new ResponseEntity<Status>(new Status(1, "Thêm thành công"), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Status>(new Status(0, "Thêm thất bại"), HttpStatus.EXPECTATION_FAILED);
		}
	}
}
