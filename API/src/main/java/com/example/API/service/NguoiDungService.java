package com.example.API.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.DTO.KhachHang;
import com.example.API.entity.NguoiDung;
import com.example.API.repository.NguoiDungRepo;
import com.example.API.repository.QuyenRepo;
import com.example.API.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@RequiredArgsConstructor
@Transactional
public class NguoiDungService implements UserDetailsService{
	@Autowired
	private final NguoiDungRepo nguoiDungRepo;
	
	@Autowired
	private final QuyenRepo quyenRepo;
	
//	@Autowired
//    private PasswordEncoder passwordEncoder;
	
//	 @Autowired
//	 private JavaMailSender mailSender;
	
	public UserDetails loadUserBySdt(String sdt) {
		NguoiDung user = nguoiDungRepo.findById(sdt).orElse(null);
		return new CustomUserDetails(user);
	}
	
	public NguoiDung getNguoiDung(String sdt) {
		return nguoiDungRepo.findById(sdt).orElse(null);
	}
	
	public void save(NguoiDung user) {
		nguoiDungRepo.save(user);
	}
	
	public NguoiDung findByEmail(String email) {
		return nguoiDungRepo.findByEmail(email);
	}
	
	public KhachHang findAdminByEmail(String email) {
		KhachHang kh = new  KhachHang();
		kh.setSdt(nguoiDungRepo.findByEmail(email).getSdt());
		kh.setHoten(nguoiDungRepo.findByEmail(email).getHoten());
		kh.setDiachi(nguoiDungRepo.findByEmail(email).getDiachi());
		kh.setEmail(nguoiDungRepo.findByEmail(email).getEmail());
		return kh;
	}
 
	public List<KhachHang> listKhachHang(){
		List<KhachHang> list = new ArrayList<>();
		for(int i=0;i < nguoiDungRepo.findKhachhang().size();i++) {
			KhachHang kh = new  KhachHang();
			kh.setSdt(nguoiDungRepo.findKhachhang().get(i).getSdt());
			kh.setHoten(nguoiDungRepo.findKhachhang().get(i).getHoten());
			kh.setDiachi(nguoiDungRepo.findKhachhang().get(i).getDiachi());
			kh.setEmail(nguoiDungRepo.findKhachhang().get(i).getEmail());
			list.add(kh);
		}
		return list;
	}
	
	public List<KhachHang> listKhachHangBySdt(String sdt){
		List<KhachHang> list = new ArrayList<>();
		for(int i=0;i < nguoiDungRepo.findBySdt(sdt).size();i++) {
			KhachHang kh = new  KhachHang();
			kh.setSdt(nguoiDungRepo.findBySdt(sdt).get(i).getSdt());
			kh.setHoten(nguoiDungRepo.findBySdt(sdt).get(i).getHoten());
			kh.setDiachi(nguoiDungRepo.findBySdt(sdt).get(i).getDiachi());
			kh.setEmail(nguoiDungRepo.findBySdt(sdt).get(i).getEmail());
			list.add(kh);
		}
		return list;
	}
	
	public void dangkitaikhoan(NguoiDung nguoidung){
		nguoidung.setQuyen(quyenRepo.getReferenceById(1));
		nguoidung.setTrangthai(false);
		String randomCode = RandomString.make(64);
		nguoidung.setVerificationcode(randomCode);
		
		nguoiDungRepo.save(nguoidung);
	}
	
//	public void sendVerificationEmail(NguoiDung nguoidung, String siteURL) 
//			throws UnsupportedEncodingException, MessagingException {	
//		String subject = "Vui lòng xác thực tài khoản";
//		String senderName = "Watch store";
//		String mailContent = "<p>Chào " + nguoidung.getHoten() + ",</p>";
//		mailContent += "<p>Vui lòng nhấn vào liên kết dưới đây để xác minh đăng kí của bạn:</p>";
//		
//		String verifyURL = siteURL + "/verify?code=" + nguoidung.getVerificationcode();
//		mailContent += "<h3><a href=\"" + verifyURL + "\">XÁC NHẬN</a></h3>";
//		
//		mailContent += "<p>Xin cảm ơn<br>Watch store</p>";
//		
//		MimeMessage message = mailSender.createMimeMessage();  
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		helper.setFrom("nguyenhieu191299@gmail.com",senderName);
//		helper.setTo(nguoidung.getEmail());
//		helper.setSubject(subject);
//		helper.setText(mailContent,true);
//		
//		mailSender.send(message);
//	}
	
	public boolean verify(String verificationcode) {
	    NguoiDung user = nguoiDungRepo.findByVerificationcode(verificationcode);
	     
	    if (user == null || user.isTrangthai()) {
	        return false;
	    } else {
	        user.setVerificationcode(null);
	        user.setTrangthai(true);
	        nguoiDungRepo.save(user);
	         
	        return true;
	    }
	     
	}
	
	public boolean verifyEmail(String verificationcode, String emailmoi) {
	    NguoiDung user = nguoiDungRepo.findByVerificationcode(verificationcode);
	     
	    if (user == null) {
	        return false;
	    } else {
	        user.setVerificationcode(null);
	        user.setEmail(emailmoi);
	        nguoiDungRepo.save(user);
	         
	        return true;
	    }
	     
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		NguoiDung user = nguoiDungRepo.findByEmail(email);
	        if (user == null) {
	            throw new UsernameNotFoundException(email);
	        }
	        return new CustomUserDetails(user);
	}
}
