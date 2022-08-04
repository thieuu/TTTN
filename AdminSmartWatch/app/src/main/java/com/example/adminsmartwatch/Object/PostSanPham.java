package com.example.adminsmartwatch.Object;

public class PostSanPham {
    public String tenSP;
    public int gia;
    public String moTa;
    public int idLoai;
    public int idKieu;
    public String anhSP;
    public int soLuongTon;

    public String getTenSP() {
        return tenSP;
    }

    public PostSanPham() {
    }

    public PostSanPham(String tenSP, int gia, String moTa, int idLoai, int idKieu, String anhSP, int soLuongTon) {
        this.tenSP = tenSP;
        this.gia = gia;
        this.moTa = moTa;
        this.idLoai = idLoai;
        this.idKieu = idKieu;
        this.anhSP = anhSP;
        this.soLuongTon = soLuongTon;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public int getIdKieu() {
        return idKieu;
    }

    public void setIdKieu(int idKieu) {
        this.idKieu = idKieu;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    @Override
    public String toString() {
        return "PostSanPham{" +
                "tenSP='" + tenSP + '\'' +
                ", gia=" + gia +
                ", moTa='" + moTa + '\'' +
                ", idLoai=" + idLoai +
                ", idKieu=" + idKieu +
                ", anhSP='" + anhSP + '\'' +
                ", soLuongTon=" + soLuongTon +
                '}';
    }
}
