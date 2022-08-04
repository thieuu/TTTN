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

public class ChoDuyetFragment extends Fragment {
    public static ArrayList<DonHang> list_donHang_choDuyet;
    DonHangAdapter donHangAdapter;
    ListView listView_choDuyet;
    public static TextView textView_choDuyet;

    public ChoDuyetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cho_duyet, container, false);
        listView_choDuyet = view.findViewById(R.id.listView_donHang_choDuyet);
        textView_choDuyet = view.findViewById(R.id.textView_donHang_choDuyet);

        list_donHang_choDuyet = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(getActivity(),list_donHang_choDuyet);
        listView_choDuyet.setAdapter(donHangAdapter);
        duLieu();

        return view;
    }

    private void duLieu(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        DuLieuDonHang.duLieu(requestQueue,list_donHang_choDuyet,textView_choDuyet,donHangAdapter,"Chờ duyệt");
    }
}