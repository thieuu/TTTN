package com.example.adminsmartwatch.Object;

public class KhachHang {
    private String sdt;
    private String tenKH;
    private String diaChi;
    private String email;

    public KhachHang(String sdt, String tenKH, String diaChi, String email) {
        this.sdt = sdt;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
