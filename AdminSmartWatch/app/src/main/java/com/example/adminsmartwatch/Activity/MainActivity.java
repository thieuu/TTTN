package com.example.adminsmartwatch.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminsmartwatch.R;

public class MainActivity extends AppCompatActivity  {
    LinearLayout linearLayout_main_donHang,linearLayout_main_nhapHang,linearLayout_main_sanPham,linearLayout_main_khachHang,linearLayout_main_thongKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller();
        setEvent();
        actionBar();
    }

    private void controller() {
        linearLayout_main_donHang = findViewById(R.id.linearLayout_main_donHang);
        linearLayout_main_nhapHang = findViewById(R.id.linearLayout_main_nhapHang);
        linearLayout_main_sanPham = findViewById(R.id.linearLayout_main_sanPham);
        linearLayout_main_khachHang = findViewById(R.id.linearLayout_main_khachHang);
        linearLayout_main_thongKe = findViewById(R.id.linearLayout_main_thongKe);
    }

    private void setEvent() {
        linearLayout_main_donHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DonHangActivity.class);
                startActivity(intent);
            }
        });
        linearLayout_main_nhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Danhsach_PhieunhapActivity.class);
                startActivity(intent);
            }
        });
        linearLayout_main_sanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SanPhamActivity.class);
                startActivity(intent);
            }
        });
        linearLayout_main_khachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KhachHangActivity.class);
                startActivity(intent);
            }
        });
        linearLayout_main_thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ThongKeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TRANG CHỦ");
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