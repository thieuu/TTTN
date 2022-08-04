package com.example.adminsmartwatch.Object;

public class PhieuNhap {
    private String idsp;
    private String ngayNhap;

    public PhieuNhap(String idsp, String ngayNhap) {
        this.idsp = idsp;
        this.ngayNhap = ngayNhap;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
}
