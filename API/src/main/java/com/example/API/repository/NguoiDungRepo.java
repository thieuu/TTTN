package com.example.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.entity.NguoiDung;

@Repository
@Transactional
public interface NguoiDungRepo extends JpaRepository<NguoiDung, String> {
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.idquyen = 1 and nd.sdt LIKE %?1%",nativeQuery = true)
	List<NguoiDung> findBySdt(String sdt);
	
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.idquyen = 1 and nd.sdt LIKE ?1",nativeQuery = true)
	NguoiDung findKHBySdt(String sdt);
	
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.idquyen = 2 and nd.sdt LIKE ?1",nativeQuery = true)
	NguoiDung findOneBySdt(String sdt);
	
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.email LIKE ?1",nativeQuery = true)
	NguoiDung findByEmail(String email);
	
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.idquyen = 1 and nd.trangthai = 1",nativeQuery = true)
	List<NguoiDung> findKhachhang();
	
	@Query(value = "SELECT * FROM nguoi_dung nd WHERE nd.verificationcode = ?1",nativeQuery = true)
	NguoiDung findByVerificationcode(String verificationcode);
	
	@Query(value = "update nguoi_dung set sdt=?1, hoten=?2, diachi=?3, verificationcode=?4 where email=?5",nativeQuery = true)
	void update(String sdt, String hoten, String diachi, String verificationcode, String email);
}
