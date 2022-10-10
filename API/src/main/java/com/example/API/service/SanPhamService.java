package com.example.API.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.API.DTO.SanPhamDTO;
import com.example.API.entity.SanPham;
import com.example.API.repository.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional 
public class SanPhamService {

	@Autowired
	private final SanPhamRepository repo;
	
	public List<SanPhamDTO> listEnable(){
		List<SanPhamDTO> list = new ArrayList<>();
		for(int i = 0;i<repo.getEnable().size();i++) {
			list.add(convertSanPhamToDTO(repo.getEnable().get(i)));
		}
		return list;
	}
	
	public List<SanPhamDTO> listDisable(){
		List<SanPhamDTO> list = new ArrayList<>();
		for(int i = 0;i<repo.getDisable().size();i++) {
			list.add(convertSanPhamToDTO(repo.getDisable().get(i)));
		}
		return list;
	}
	
	public List<SanPhamDTO> listByTen(String tensanpham){
		List<SanPhamDTO> list = new ArrayList<>();
		for(int i = 0;i<repo.findByTen(tensanpham).size();i++) {
			list.add(convertSanPhamToDTO(repo.findByTen(tensanpham).get(i)));
		}
		return list;
	}
	
	public List<SanPham> listLoai(Integer maLoai){
		return repo.listLoai(maLoai);
	}
	
	public List<SanPham> lisHangSX(Integer maHangSX){
		return repo.listHangSX(maHangSX);
	}
	
	public List<SanPham> listLoaiAndHangSX(Integer maLoai,Integer maHangSX){
		return repo.timSP(maLoai, maHangSX);
	}
	
	public void save(SanPham sanPham) {
		repo.save(sanPham);
	}
	
	public SanPham get(Integer maSP) {
		return repo.findById(maSP).orElse(null);
	}
	
	public void delete(Integer maSP) {
		repo.deleteById(maSP);
	}
	
	private SanPhamDTO convertSanPhamToDTO(SanPham sanPham) {
		SanPhamDTO sanPhamDTO = new SanPhamDTO();
		sanPhamDTO.setMasanpham(sanPham.getMasanpham());
		sanPhamDTO.setTensanpham(sanPham.getTensanpham());
		sanPhamDTO.setMota(sanPham.getMota());
		sanPhamDTO.setGiaban(sanPham.getGiaban());
		sanPhamDTO.setAnhsanpham(sanPham.getAnhsanpham());
		sanPhamDTO.setTenloai(sanPham.getLoaisanpham().getTenloai());
		sanPhamDTO.setTenhang(sanPham.getHangsanxuat().getTenhang());
		sanPhamDTO.setSoluongton(sanPham.getSoluongton());
		sanPhamDTO.setTrangthai(sanPham.isTrangthai());
		return sanPhamDTO;	
	}
}
