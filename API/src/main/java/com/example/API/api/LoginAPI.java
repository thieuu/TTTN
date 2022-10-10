package com.example.API.api;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.DTO.ChangePassword;
import com.example.API.DTO.LoginRequest;
import com.example.API.DTO.LoginResponse;
import com.example.API.DTO.Status;
import com.example.API.entity.NguoiDung;
import com.example.API.repository.NguoiDungRepo;
import com.example.API.security.CustomUserDetails;
import com.example.API.security.JwtTokenProvider;
import com.example.API.service.NguoiDungService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin
@RestController
public class LoginAPI {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private NguoiDungService service;

	@Autowired
	private NguoiDungRepo nguoiDungRepo;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Xác thực từ username và password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getTendn(), loginRequest.getMatkhau()));
		// Nếu không xảy ra exception tức là thông tin hợp lệ
		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Trả về jwt cho người dùng.
		CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
		String jwt = tokenProvider.generateToken(userDetail);
		return new LoginResponse(jwt, userDetail.getUser().getQuyen().getTenquyen());
	}

	@PutMapping("/doimatkhau")
	private ResponseEntity<Status> change(@RequestBody ChangePassword change) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(change.getTendn(), change.getMatkhau()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

		if (jwt != null) {
			try {
				NguoiDung exUser = service.findByEmail(change.getTendn());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

				if (exUser != null) {
					NguoiDung newPass = new NguoiDung(exUser.getSdt(), exUser.getHoten(), exUser.getDiachi(),
							exUser.getEmail(), encoder.encode(change.getMatKhauMoi()), exUser.getQuyen(),
							exUser.isTrangthai());
					System.out.println(newPass.toString());
					service.save(newPass);
					return new ResponseEntity<Status>(new Status(1, "Đổi mật khẩu thành công"), HttpStatus.OK);
				}

			} catch (NoSuchElementException e) {
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<Status>(new Status(0, "Đổi mật khẩu thất bại"), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/quenmatkhau")
	public ResponseEntity<Status> quenmatkhau(@RequestParam String email)
			throws UnsupportedEncodingException, MessagingException {
		String randomCode = RandomString.make(8);
		NguoiDung nguoiDung = nguoiDungRepo.findByEmail(email);
		if (nguoiDung != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			nguoiDung.setMatkhau(encoder.encode(randomCode));
			String subject = "Mật khẩu của bạn đã được thay đổi";
			String senderName = "Watch store";
			String mailContent = "<p>Chào " + nguoiDung.getHoten() + ",</p>";
			mailContent += "<p>Mật khẩu của bạn đã được thay đổi.</p>";
			mailContent += "<p>Hãy đăng nhập với mật khẩu mới:" + randomCode + "</p>";
			mailContent += "<p>Xin cảm ơn<br>Watch store</p>";
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("nguyenhieu191299@gmail.com", senderName);
			helper.setTo(nguoiDung.getEmail());
			helper.setSubject(subject);
			helper.setText(mailContent, true);

			mailSender.send(message);
			
			System.out.println(randomCode);
			nguoiDungRepo.save(nguoiDung);
			return new ResponseEntity<Status>(new Status(1, "Hãy kiểm tra email để nhận mật khẩu mới"),
					HttpStatus.OK);
		}
		return new ResponseEntity<Status>(new Status(0, "Kiểm tra lại thông tin email"), HttpStatus.BAD_REQUEST);
	}

}
