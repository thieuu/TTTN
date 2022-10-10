package com.example.API.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@DynamicUpdate
public class NguoiDung {
    @Id
    private String sdt;
    private String hoten;
    private String diachi;
    private String email;
    private String matkhau;
    
    @ManyToOne
    @JoinColumn(name = "idquyen")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Quyen quyen;
    
    private boolean trangthai;
    
    @Column(name = "verificationcode", length = 64)
    private String verificationcode;
    
    public NguoiDung(String sdt, String hoten, String diachi, String email, String matkhau, Quyen quyen,
			boolean trangthai) {
		super();
		this.sdt = sdt;
		this.hoten = hoten;
		this.diachi = diachi;
		this.email = email;
		this.matkhau = matkhau;
		this.quyen = quyen;
		this.trangthai = trangthai;
	}

	@JsonIgnore
    @OneToMany(mappedBy = "nguoidung",cascade = CascadeType.PERSIST)
    private Set<DonHang> donhang;
    
    @JsonIgnore
    @OneToMany(mappedBy = "nguoidung",cascade = CascadeType.PERSIST)
    private Set<PhieuNhap> phieunhap;
}
