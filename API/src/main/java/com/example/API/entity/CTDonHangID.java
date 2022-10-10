package com.example.API.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class CTDonHangID implements Serializable {
    private int madonhang;
    private int masanpham;
	
//	private DonHang donhang;
//	private SanPham sanpham;
}
