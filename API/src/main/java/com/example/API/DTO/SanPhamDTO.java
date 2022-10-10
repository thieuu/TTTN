package com.example.API.DTO;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;

@Data
public class SanPhamDTO {
	private int masanpham;
    private String tensanpham;
    private String mota;
    private int giaban;
    public String anhsanpham;
    private String tenloai;
    private String tenhang;
    private int soluongton;
    private boolean trangthai;
    
    public String getAnhsanpham() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(anhsanpham).toUriString();
	}
}
