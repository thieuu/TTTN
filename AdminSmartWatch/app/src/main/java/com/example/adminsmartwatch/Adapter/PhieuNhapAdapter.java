package com.example.adminsmartwatch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adminsmartwatch.Object.PhieuNhap;
import com.example.adminsmartwatch.R;

import java.util.ArrayList;

public class PhieuNhapAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhieuNhap> list;

    public PhieuNhapAdapter(Context context, ArrayList<PhieuNhap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_danhsachphieunhap, null);
        TextView textView_SoPhieu = row.findViewById(R.id.tvSoPhieu);
        TextView textView_NgayNhap = row.findViewById(R.id.tvNgayNhap);
        LinearLayout linearLayout_PhieuNhap= row.findViewById(R.id.linearLayout_row_PhieuNhap);

        PhieuNhap pNhap = list.get(position);
        textView_NgayNhap.setText(pNhap.getNgayNhap());
        textView_SoPhieu.setText(pNhap.getIdsp());
//        linearLayout_PhieuNhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, CTDonHangActivity.class);
//                intent.putExtra("donhang", donHang);
//                context.startActivity(intent);
//            }
//        });


        return row;
    }
}
