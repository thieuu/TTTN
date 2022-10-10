package com.example.API.entity;

import javax.persistence.Entity;

//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author tHieu
 *
 */

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@DynamicUpdate
public class SanPham {
    @Id
    @Column(name = "masanpham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int masanpham;
    private String tensanpham;
    private int giaban;
    private String anhsanpham;
    private String mota;

    @ManyToOne
    @JoinColumn(name = "maloai")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LoaiSanPham loaisanpham;

    
    @ManyToOne
    @JoinColumn(name = "mahang")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HangSanXuat hangsanxuat;   
    
    private int soluongton;
    private boolean trangthai;
    
//	public String getAnhsanpham() {
//		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(anhsanpham).toUriString();
//	}
    
	@JsonIgnore
	@OneToMany(mappedBy = "sanpham",cascade = CascadeType.PERSIST)
    private Set<CTPhieuNhap> ctphieunhap;

	@JsonIgnore
    @OneToMany(mappedBy = "sanpham",cascade = CascadeType.PERSIST)
    private Set<CTDonHang> ctdonhang;
}
