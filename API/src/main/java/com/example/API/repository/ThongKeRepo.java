package com.example.API.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.API.DTO.ThongKe;

@Repository
public interface ThongKeRepo extends JpaRepository<ThongKe, String> {
	@Query(value="EXEC SPThongKe @THANG = ?1,@NAM = ?2",nativeQuery = true)
	List<ThongKe> listThongKe(int thang,int nam);
}
