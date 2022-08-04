package com.example.adminsmartwatch.Activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Adapter.DonHangAdapter;
import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.DuLieuDonHang;

import java.util.ArrayList;

public class DangGiaoFragment extends Fragment {
    public static ArrayList<DonHang> list_donHang_dangGiao;
    DonHangAdapter donHangAdapter;
    ListView listView_dangGiao;
    TextView textView_dangGiao;

    public DangGiaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_giao, container, false);
        listView_dangGiao = view.findViewById(R.id.listView_donHang_dangGiao);
        textView_dangGiao= view.findViewById(R.id.textView_donHang_dangGiao);

        list_donHang_dangGiao = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(getActivity(),list_donHang_dangGiao);
        listView_dangGiao.setAdapter(donHangAdapter);
        duLieu();


        return view;
    }


    private void duLieu(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        DuLieuDonHang.duLieu(requestQueue,list_donHang_dangGiao,textView_dangGiao,donHangAdapter,"Đang giao");

    }
}