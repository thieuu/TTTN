package com.example.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.API.entity.HangSanXuat;

@Repository
public interface HangSXRepository extends JpaRepository<HangSanXuat, Integer> {
	@Query(value = "SELECT * FROM hang_san_xuat hsx WHERE hsx.tenhang LIKE ?1",nativeQuery = true)
	HangSanXuat findByTenHang(String tenhang);
}
