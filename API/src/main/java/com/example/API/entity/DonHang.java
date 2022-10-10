package com.example.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int madonhang;

    @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
    private Date ngaydathang;								

    @ManyToOne
    @JoinColumn(name="sdt")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NguoiDung nguoidung;

    private String trangthai;
    
    @OneToMany(mappedBy = "donhang",cascade = CascadeType.PERSIST)
    private List<CTDonHang> ctdonhang;
}
