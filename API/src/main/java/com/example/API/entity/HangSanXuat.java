package com.example.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class HangSanXuat{
    @Id
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    private int mahang;
    private String tenhang;
    
    @JsonIgnore
	@OneToMany(mappedBy = "hangsanxuat",cascade = CascadeType.PERSIST)
    private Set<SanPham> sanpham;
}
