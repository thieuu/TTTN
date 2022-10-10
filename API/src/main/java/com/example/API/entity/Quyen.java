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
public class Quyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idquyen;
    private String tenquyen;
    
    @JsonIgnore
    @OneToMany(mappedBy = "quyen",cascade = CascadeType.PERSIST)
    private Set<NguoiDung> nguoidung;
}
