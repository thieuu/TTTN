package com.example.API.api;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.DTO.KhachHang;
import com.example.API.DTO.Status;
import com.example.API.entity.NguoiDung;
import com.example.API.repository.NguoiDungRepo;
import com.example.API.security.CustomUserDetails;
import com.example.API.security.JwtTokenProvider;
import com.example.API.security.Utility;
import com.example.API.service.NguoiDungService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin
@RestController
public class NguoiDungAPI {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	
	@Autowired
	private NguoiDungService service;
	
	@Autowired
	private NguoiDungRepo repo;
	
	@GetMapping("/khachhang")
	private List<KhachHang> list(){
		return service.listKhachHang();
	}
	
	@GetMapping("/khachhang/{sdt}")
	private List<KhachHang> get(@PathVariable String sdt){
		return service.listKhachHangBySdt(sdt);
	}
	
	@GetMapping("/khachhang/sdt/{sdt}")
	private NguoiDung getBySdt(@PathVariable String sdt){
		return repo.findKHBySdt(sdt);
	}
	
	@GetMapping("/khachhang/email/{email}")
	private NguoiDung findKHByEmail(@PathVariable String email){
		return service.findByEmail(email);
	}
	
	@GetMapping("/admin/{email}")
	private KhachHang getByEmail(@PathVariable String email){
		return service.findAdminByEmail(email);
	}
	
	 @Autowired
	 private JavaMailSender mailSender;
	 
	 public void sendVerificationEmail(NguoiDung nguoidung, String siteURL) 
				throws UnsupportedEncodingException, MessagingException {	
			String subject = "Vui lòng xác thực tài khoản";
			String senderName = "Watch store";
			String mailContent = "<p>Chào " + nguoidung.getHoten() + ",</p>";
			mailContent += "<p>Vui lòng nhấn vào liên kết dưới đây để xác minh đăng kí của bạn:</p>";
			
			String verifyURL = siteURL + "/verify?code=" + nguoidung.getVerificationcode();
			mailContent += "<h3><a href=\"" + verifyURL + "\">XÁC NHẬN</a></h3>";
			
			mailContent += "<p>Xin cảm ơn<br>Watch store</p>";
			
			MimeMessage message = mailSender.createMimeMessage();  
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("nguyenhieu191299@gmail.com",senderName);
			helper.setTo(nguoidung.getEmail());
			helper.setSubject(subject);
			helper.setText(mailContent,true);
			
			mailSender.send(message);
		}
	 
	@PostMapping("/dangki")
	private ResponseEntity<Status> dangki(@RequestBody NguoiDung nguoidung,HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException{
		
		System.out.println(nguoidung.toString());
		NguoiDung nDung = service.getNguoiDung(nguoidung.getSdt());
		NguoiDung nDung2 = service.findByEmail(nguoidung.getEmail());
		if(nDung == null && nDung2 == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			nguoidung.setMatkhau(encoder.encode(nguoidung.getMatkhau()));
			
			String siteURL = Utility.getSiteURL(request);
			
			service.dangkitaikhoan(nguoidung);
			
			sendVerificationEmail(nguoidung, siteURL);
			return new ResponseEntity<Status>(new Status(1,"Đăng kí thành công"),HttpStatus.OK);
		} else return new ResponseEntity<Status>(new Status(0,"Số điện thoại hoặc email đã được sử dụng"),HttpStatus.OK);
	}
	
	@PutMapping("/suathongtin")
	private ResponseEntity<Status> suathongtin(@RequestBody NguoiDung nguoiDung) throws AuthenticationException{
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(nguoiDung.getEmail(), nguoiDung.getMatkhau()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

		if (jwt != null) {
			try {
				NguoiDung nDung = service.findByEmail(nguoiDung.getEmail());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

				if (nDung != null) {
					nDung.setHoten(nguoiDung.getHoten());
					//nDung.setSdt(nguoiDung.getSdt());
					nDung.setDiachi(nguoiDung.getDiachi());
					
					String randomCode = RandomString.make(64);
					nDung.setVerificationcode(randomCode);
					
//					Query query=HibernateSessionFactory.getSession().createQuery(update table set id=? where id=?);
//					query.executeUpdate();
//					HibernateSessionFactory.getSession().beginTransaction().commit();
							
					//repo.update(nDung.getSdt(), nDung.getDiachi(), nDung.getHoten(), randomCode, nguoiDung.getEmail());
					repo.save(nDung);
					return new ResponseEntity<Status>(new Status(1, "Đổi thông tin thành công"), HttpStatus.OK);
				}
			} catch (NoSuchElementException e) {
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<Status>(new Status(0,"Kiểm tra lại mật khẩu"),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/xacthuc")
	private ResponseEntity<Status> xacthucmail(@RequestParam String email, @RequestParam String emailmoi, HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException {
		System.out.println("===========Xác thực======"+emailmoi);
		NguoiDung nDung = service.findByEmail(email);
		NguoiDung nDung2 = service.findByEmail(emailmoi);
		if(nDung != null && nDung2 == null) {
			String subject = "Vui lòng xác thực tài khoản";
			String senderName = "Watch store";
			String mailContent = "<p>Chào " + nDung.getHoten() + ",</p>";
			mailContent += "<p>Vui lòng nhấn vào liên kết dưới đây để xác minh thay đổi email của bạn:</p>";
			
			String siteURL = Utility.getSiteURL(request);
			String verifyURL = siteURL + "/verify/email?code=" + nDung.getVerificationcode() + "&emailmoi=" + emailmoi;
			mailContent += "<h3><a href=\"" + verifyURL + "\">XÁC NHẬN</a></h3>";
			
			mailContent += "<p>Xin cảm ơn<br>Watch store</p>";
			
			MimeMessage message = mailSender.createMimeMessage();  
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("nguyenhieu191299@gmail.com",senderName);
			helper.setTo(emailmoi);
			helper.setSubject(subject);
			helper.setText(mailContent,true);
			
			mailSender.send(message);
			return new ResponseEntity<Status>(new Status(1, "Gửi thành công"), HttpStatus.OK);
		}
		return new ResponseEntity<Status>(new Status(0,"Kiểm tra lại email"),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/verify/email")
	public String verifyEmail(@RequestParam String code, @RequestParam String emailmoi) {
	    if (service.verifyEmail(code, emailmoi)) {
	        return "Xác thực email thành công";
	    } else {
	        return "Xác thực email thất bại";
	    }
	}
	
	@GetMapping("/verify")
	public String verifyUser(@RequestParam String code) {
	    if (service.verify(code)) {
	        return "Xác thực tài khoản thành công";
	    } else {
	        return "Xác thực tài khoản thất bại";
	    }
	}
}
