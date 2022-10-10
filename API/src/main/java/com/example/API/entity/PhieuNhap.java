package com.example.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class PhieuNhap  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maphieunhap;

    @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
    private Date ngaynhap;

    @ManyToOne
    @JoinColumn(name="sdt")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NguoiDung nguoidung;
    
    @JsonIgnore
    @OneToMany(mappedBy = "phieunhap",cascade = CascadeType.PERSIST)
	private Set<CTPhieuNhap> ctphieunhap;
}
