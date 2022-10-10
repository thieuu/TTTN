package com.example.API.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.API.entity.DonHang;
import com.example.API.entity.PhieuNhap;

public interface DonHangRepo extends JpaRepository<DonHang, Integer> {
	@Query(value = "SELECT * FROM don_hang dh WHERE dh.sdt = ?1",nativeQuery = true)
	List<DonHang> findBySdt(String sdt);
	
	@Query(value = "SELECT * FROM don_hang dh WHERE dh.ngaydathang = ?1 and dh.sdt = ?2",nativeQuery = true)
	DonHang getdonhang(Date ngaydathang,String sdt);
}
