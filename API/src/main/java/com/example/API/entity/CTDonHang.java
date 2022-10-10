package com.example.API.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Data @AllArgsConstructor 
//@IdClass(CTDonHangID.class)
public class CTDonHang {

	@EmbeddedId
	@JsonIgnore
	private CTDonHangID id;
	public CTDonHang() {
        id = new CTDonHangID();
    }
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madonhang", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private DonHang donhang;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masanpham", insertable=false, updatable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SanPham sanpham;

//	@Id
//	@Column(name = "madonhang")
//	private int madonhang;
//	
//	@Id
//	@Column(name = "masanpham")
//	private int masanpham;
    
    private int soluong;
    private int giamua;
    
//	public void setId(CTDonHangID id) {
//		this.id = new CTDonHangID();
//	}
    
}
