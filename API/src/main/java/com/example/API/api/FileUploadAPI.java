package com.example.API.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.API.DTO.*;
import com.example.API.service.FileUploadHelper;

@CrossOrigin
@RestController
public class FileUploadAPI {
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@PostMapping("/upfile")
	public ResponseEntity<Status> uploadFile(@RequestPart("file") MultipartFile file,HttpServletRequest request){
		try {
			//String path = request.getServletContext().getRealPath("");
			//System.out.println("Path:"+path);
			if(file.isEmpty()) {
				System.out.println("Không tìm thấy file lưu trữ");
				return new ResponseEntity<Status>(new Status(0,"Khong tim thay file luu tru"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png") && !file.getContentType().equals("image/webp")) {
				System.out.println("Không đúng định dạng file");
				return new ResponseEntity<Status>(new Status(0,"Khong dung dinh dang file"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			int f = fileUploadHelper.uploadFile(file);
			if(f==1) {
				System.out.println("thành công" + ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
				return new ResponseEntity<Status>(new Status(1,"Upload thanh cong"),HttpStatus.OK);
				//return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
			}
			if(f==2) {
				System.out.println("File đã tồn tại.Đổi tên file để thêm mới...");
				return new ResponseEntity<Status>(new Status(0,"File da ton tai"),HttpStatus.INTERNAL_SERVER_ERROR);
				//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File đã tồn tại.Đổi tên file để thêm mới..."); 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Status>(new Status(0,"Upload that bai"),HttpStatus.INTERNAL_SERVER_ERROR);
		//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thất bại ! Thử lại"); 
	}
}