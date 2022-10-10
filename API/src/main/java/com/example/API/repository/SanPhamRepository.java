package com.example.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.API.DTO.ThongKe;
import com.example.API.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.trangthai = 1",nativeQuery = true)
	List<SanPham> getEnable();
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.trangthai = 0",nativeQuery = true)
	List<SanPham> getDisable();
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.maloai = ?1 and sp.trangthai = 1",nativeQuery = true)
	List<SanPham> listLoai(Integer maloai);	
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.mahang = ?1 and sp.trangthai = 1",nativeQuery = true)
	List<SanPham> listHangSX(Integer mahang);
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.maloai = ?1 and sp.mahang = ?2 and sp.trangthai = 1",nativeQuery = true)
	List<SanPham> timSP(Integer maloai,Integer mahang);
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.tensanpham LIKE %?1%",nativeQuery = true)
	List<SanPham> findByTen(String tensanpham);
	
	@Query(value = "SELECT * FROM san_pham sp WHERE sp.giaban = ?1",nativeQuery = true)
	List<SanPham> listsanpham(Integer giaban);

}
