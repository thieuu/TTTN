package com.example.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.entity.CTDonHang;
import com.example.API.entity.CTPhieuNhap;
import com.example.API.entity.CTPhieuNhapID;

@Repository
@Transactional
public interface CTPhieuNhapRepo extends JpaRepository<CTPhieuNhap, Class<CTPhieuNhapID>> {
	@Query(value = "SELECT * FROM ctphieu_nhap ctpn WHERE ctpn.maphieunhap = ?1",nativeQuery = true)
	List<CTPhieuNhap> findByMaphieunhap(int maphieunhap);
	
	@Modifying
	@Query(value = "insert into ctphieu_nhap (gianhap, so_luong, maphieunhap, masanpham) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	void insertCTPN(int gia,int soluong, int maphieunhap, int masanpham);

}
