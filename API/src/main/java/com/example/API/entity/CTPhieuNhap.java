package com.example.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity  @AllArgsConstructor
//@IdClass(CTPhieuNhapID.class)
public class CTPhieuNhap {
	
	@EmbeddedId
	@JsonIgnore
	private CTPhieuNhapID id;
	public CTPhieuNhap() {
        id = new CTPhieuNhapID();
    }
	
    @ManyToOne
    @JoinColumn(name = "maphieunhap", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PhieuNhap phieunhap;

    @ManyToOne
    @JoinColumn(name = "masanpham", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SanPham sanpham;
    
//	@Id
//	@Column(name = "maphieunhap")
//	private int maphieunhap;
//	
//	@Id
//	@Column(name = "masanpham")
//	private int masanpham;

    private int soLuong;
    private int gianhap;
    
    public void setId(CTPhieuNhapID id) {
		this.id = new CTPhieuNhapID();
	}
}
