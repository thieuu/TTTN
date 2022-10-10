package com.example.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.entity.CTDonHang;
import com.example.API.entity.CTDonHangID;

@Repository
@Transactional
public interface CTDonHangRepo extends JpaRepository<CTDonHang, Class<CTDonHangID>> {
	@Query(value = "SELECT * FROM ctdon_hang ctdh WHERE ctdh.madonhang = ?1",nativeQuery = true)
	List<CTDonHang> findByMadonhang(int madonhang);
	
	@Modifying
	@Query(value = "insert into ctdon_hang (giamua, soluong, madonhang, masanpham) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	void insertCTDH(int gia,int soluong, int madonhang, int masanpham);
}
