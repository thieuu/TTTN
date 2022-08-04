package com.example.adminsmartwatch.Object;

public class ThongKe {
    private String tensanpham;
    private int tongsoluong;

    public ThongKe(String tensanpham, int tongsoluong) {
        this.tensanpham = tensanpham;
        this.tongsoluong = tongsoluong;
    }

    public ThongKe() {
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getTongsoluong() {
        return tongsoluong;
    }

    public void setTongsoluong(int tongsoluong) {
        this.tongsoluong = tongsoluong;
    }
}
