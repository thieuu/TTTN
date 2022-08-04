package com.example.adminsmartwatch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adminsmartwatch.Activity.CTKhachHangActivity;
import com.example.adminsmartwatch.Object.KhachHang;
import com.example.adminsmartwatch.R;

import java.util.ArrayList;

public class KhachHangAdapter extends BaseAdapter {
    ArrayList<KhachHang> arrayListKH;
    Context context;


    public KhachHangAdapter(ArrayList<KhachHang> arrayListKH, Context context) {
        this.arrayListKH = arrayListKH;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListKH.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListKH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_khachhang, null);
        TextView textView_tenKH = row.findViewById(R.id.textView_khachHang_ten);
        TextView textView_sdt = row.findViewById(R.id.textView_khachHang_sdt);
        TextView textView_diaChi = row.findViewById(R.id.textView_khachHang_diaChi);
        TextView textView_email = row.findViewById(R.id.textView_khachHang_email);
        LinearLayout linearLayout_khachHang = row.findViewById(R.id.linearLayout_row_khachHang);

        KhachHang khachHang = arrayListKH.get(position);
        textView_diaChi.setText(khachHang.getDiaChi());
        textView_tenKH.setText(khachHang.getTenKH());
        textView_email.setText(khachHang.getEmail());
        textView_sdt.setText(khachHang.getSdt());


        linearLayout_khachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CTKhachHangActivity.class);
                intent.putExtra("sdt", khachHang.getSdt());
                intent.putExtra("ten", khachHang.getTenKH());
                intent.putExtra("diaChi", khachHang.getDiaChi());
                intent.putExtra("email", khachHang.getEmail());
                context.startActivity(intent);
            }
        });
        return row;
    }

}
