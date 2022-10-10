package com.example.API.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	
//	public String UPLOAD_DIR=new ClassPathResource("static/image/").getFile().getAbsolutePath();
//	public String con = "\\target";
//	public String UPLOAD_DIR = cha.substring(0,cha.indexOf(con)) + "\\src\\main\\resources\\static\\image";
	
	String UPLOAD_DIR = System.getProperty("user.dir")+"/src/main/resources/static/image";
	String UPLOAD_DIR_copy = System.getProperty("user.dir")+"/target/classes/static/image";
	
	public FileUploadHelper() throws IOException {
	}


	public int uploadFile(MultipartFile multipartFile)
	{
		System.err.println("UPLOAD_DIR：" + UPLOAD_DIR);
		System.err.println("UPLOAD_DIR_copy：" + UPLOAD_DIR_copy);
		int f=0;
		try {
//			InputStream is = multipartFile.getInputStream();
//			byte data[] = new byte[is.available()];
//			is.read(data);
//			
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+"\\"+multipartFile.getOriginalFilename());
//			fos.write(data);
//			fos.flush();
//			fos.close();
//			System.out.println(cha);
//			System.out.println(UPLOAD_DIR);
			if(new File(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()).exists()) {
				f = 2;
			} else {
				Files.copy(multipartFile.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
				Files.copy(multipartFile.getInputStream(),Paths.get(UPLOAD_DIR_copy+File.separator+multipartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
				f=1;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
