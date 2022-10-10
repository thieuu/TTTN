package com.example.API.entity;
import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class CTPhieuNhapID implements Serializable {
    private int maphieunhap;
    private int masanpham;
	
//	private PhieuNhap phieunhap;
//	private SanPham sanpham;
}
