package com.example.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class LoaiSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maloai;
    private String tenloai;
    
    @JsonIgnore
	@OneToMany(mappedBy = "loaisanpham",cascade = CascadeType.PERSIST)
    private Set<SanPham> sanpham;
}

