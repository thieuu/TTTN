package com.example.adminsmartwatch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.R;

import java.util.ArrayList;

public class DonHang_KhachHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonHang> list;

    public DonHang_KhachHangAdapter(Context context, ArrayList<DonHang> list) {
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
        View row = inflater.inflate(R.layout.row_donhang, null);
        TextView textView_idDH = row.findViewById(R.id.textView_donHang_idDH);
        TextView textView_ngay = row.findViewById(R.id.textView_donHang_ngay);
        TextView textView_gio = row.findViewById(R.id.textView_donHang_gio);
        TextView textView_sdt = row.findViewById(R.id.textView_donHang_sdt);
        Button button_duyetDon = row.findViewById(R.id.button_donHang_duyet);
        Button button_huyDon = row.findViewById(R.id.button_donHang_huy);
        LinearLayout linearLayout_donHang = row.findViewById(R.id.linearLayout_row_donHang);

        DonHang donHang = list.get(position);

        textView_idDH.setText(donHang.idDH + "");
        textView_ngay.setText(donHang.ngay);
        textView_gio.setText(donHang.gio);
        textView_sdt.setText(donHang.sdt);
        button_duyetDon.setVisibility(View.GONE);
        button_huyDon.setVisibility(View.GONE);

        return row;
    }
}
