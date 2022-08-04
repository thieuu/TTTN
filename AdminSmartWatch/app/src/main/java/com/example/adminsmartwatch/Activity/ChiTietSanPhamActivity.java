package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminsmartwatch.Object.SanPham;
import com.example.adminsmartwatch.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    TextView textView_tenSP, textView_giaSP, textView_loaiSP, textView_kieuSP, textView_soLuongTon, textView_moTaSP;
    ImageView imageView_hinhSP;
    Button button_sua,button_xoa;

    SanPham sanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sp);

        Controller();
        actionBar();
        SetEvent();
    }


    private void Controller() {

        textView_giaSP = findViewById(R.id.textView_chiTietSP_giaSP);
        textView_tenSP = findViewById(R.id.textView_chiTietSP_tenSP);
        textView_kieuSP = findViewById(R.id.textView_chiTietSP_kieuSP);
        textView_loaiSP = findViewById(R.id.textView_chiTietSP_loaiSP);
        textView_soLuongTon = findViewById(R.id.textView_chiTietSP_soLuongTon);
        textView_moTaSP = findViewById(R.id.textView_chiTietSP_moTaSP);
        imageView_hinhSP = findViewById(R.id.image_chiTiet_hinhSP);
        button_sua = findViewById(R.id.button_chiTiet_sanPham_sua);
        button_xoa = findViewById(R.id.button_chiTiet_sanPham_xoa);

    }


    private void SetEvent() {
        sanPham = getIntent().getParcelableExtra("sanpham");

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textView_giaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) + " Đ");
        textView_tenSP.setText(sanPham.getTenSP());
        textView_loaiSP.setText(sanPham.getLoaiSP());
        textView_kieuSP.setText(sanPham.getKieuSP());
        textView_soLuongTon.setText(sanPham.getSoLuongSP() + "");
        if (sanPham.getMotaSP().equals("null")) {
            textView_moTaSP.setText(sanPham.getTenSP());
        } else {
            textView_moTaSP.setText(sanPham.getMotaSP());
        }
        Picasso.with(getApplicationContext()).load(sanPham.getHinhSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(imageView_hinhSP);
    }


    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CHI TIẾT SẢN PHẨM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu1:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.menu2:
                Intent intent1 = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu3:
                finishAffinity();
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

}
