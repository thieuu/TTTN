package com.example.adminsmartwatch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adminsmartwatch.Object.CTDonHang;
import com.example.adminsmartwatch.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CTDonHangAdapter extends BaseAdapter {
    ArrayList<CTDonHang> arrayListCTDH;
    Context context;

    private boolean check = false;

    public CTDonHangAdapter( Context context,ArrayList<CTDonHang> arrayListCTDH) {
        this.arrayListCTDH = arrayListCTDH;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListCTDH.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListCTDH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_ct_donhang, null);
        ImageView image_sanPham = row.findViewById(R.id.image_ctDonHang_hinhSP);
        TextView textView_tenSP = row.findViewById(R.id.textView_ctDonHang_tenSP);
        TextView textView_giaSP = row.findViewById(R.id.textView_ctDonHang_giaSP);
        TextView textView_soLuong = row.findViewById(R.id.textView_ctDonHang_soLuong);

        CTDonHang ctDonHang = arrayListCTDH.get(position);
        textView_tenSP.setText(ctDonHang.getTenSP());
        textView_soLuong.setText("SL: " + ctDonHang.getSoLuong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textView_giaSP.setText("Giá: " + decimalFormat.format(ctDonHang.getGiaSP()) + " Đ");
        Picasso.with(context).load(ctDonHang.getAnhSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(image_sanPham);

        return row;
    }
}
