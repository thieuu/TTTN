package com.example.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.entity.Quyen;

public interface QuyenRepo extends JpaRepository<Quyen, Integer> {
	Quyen findByTenquyen(String tenquyen);
}
