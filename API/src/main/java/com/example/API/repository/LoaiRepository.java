package com.example.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.API.entity.LoaiSanPham;

@Repository
public interface LoaiRepository extends JpaRepository<LoaiSanPham, Integer> {
	@Query(value = "SELECT * FROM loai_san_pham lsp WHERE lsp.tenloai LIKE ?1",nativeQuery = true)
	LoaiSanPham findByTenLoai(String tenloai);
}
