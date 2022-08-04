package com.example.adminsmartwatch.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class DonHang implements Parcelable {
    public int idDH;
    public String ngay;
    public String gio;
    public String sdt;
    public String trangThai;
    public String diacChi;
    public String email;
    public String tenKH;

    public DonHang(int idDH, String ngay, String gio, String sdt, String trangThai, String diacChi, String email, String tenKH) {
        this.idDH = idDH;
        this.ngay = ngay;
        this.gio = gio;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.diacChi = diacChi;
        this.email = email;
        this.tenKH = tenKH;
    }

    public int getIdDH() {
        return idDH;
    }

    public void setIdDH(int idDH) {
        this.idDH = idDH;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiacChi() {
        return diacChi;
    }

    public void setDiacChi(String diacChi) {
        this.diacChi = diacChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    protected DonHang(Parcel in) {
        idDH = in.readInt();
        ngay = in.readString();
        gio = in.readString();
        sdt = in.readString();
        trangThai = in.readString();
        diacChi = in.readString();
        email = in.readString();
        tenKH = in.readString();
    }

    public static final Creator<DonHang> CREATOR = new Creator<DonHang>() {
        @Override
        public DonHang createFromParcel(Parcel in) {
            return new DonHang(in);
        }

        @Override
        public DonHang[] newArray(int size) {
            return new DonHang[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idDH);
        dest.writeString(ngay);
        dest.writeString(gio);
        dest.writeString(sdt);
        dest.writeString(trangThai);
        dest.writeString(diacChi);
        dest.writeString(email);
        dest.writeString(tenKH);
    }
}
