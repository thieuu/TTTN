package com.example.API.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.API.entity.PhieuNhap;

public interface PhieuNhapRepo extends JpaRepository<PhieuNhap, Integer>{
	@Query(value = "SELECT * FROM phieu_nhap pn WHERE pn.ngaynhap = ?1 and pn.sdt = ?2",nativeQuery = true)
	PhieuNhap getphieunhap(Date ngaynhap,String sdt);

}
