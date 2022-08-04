package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Adapter.KhachHangAdapter;
import com.example.adminsmartwatch.Object.KhachHang;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhachHangActivity extends AppCompatActivity {


    ListView listView_khachHang;
    ArrayList<KhachHang> list_khachHang;
    KhachHangAdapter khachHangAdapter;

    ArrayList<KhachHang> list_khachHang_sdt;
    KhachHangAdapter khachHangAdapter_sdt;

    Button button_timKiem;
    EditText editText_sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);

        Controller();
        actionBar();
        duLieuKhachHang("");
        setEvent();
    }

    private void Controller() {
        button_timKiem = findViewById(R.id.button_khachHang_sdt_timKiem);
        editText_sdt = findViewById(R.id.textView_khachHang_sdt_timKiem);

        list_khachHang = new ArrayList<>();
        listView_khachHang = findViewById(R.id.listView_khachHang);
        khachHangAdapter = new KhachHangAdapter(list_khachHang, this);

        list_khachHang_sdt = new ArrayList<>();
        khachHangAdapter_sdt = new KhachHangAdapter(list_khachHang_sdt, this);

    }

    private void setEvent(){
        button_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duLieuKhachHang(editText_sdt.getText().toString());
            }
        });
    }

    private void duLieuKhachHang(String sdtKH) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.khachHang;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            list_khachHang_sdt.clear();
                            list_khachHang.clear();
                            // Khởi tạo biến toàn cục của sản phẩm
                            String tenKH = "", sdt = "", email = "", diaChi = "";
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    tenKH = jsonObject.getString("tenKH");
                                    sdt = jsonObject.getString("sdt");
                                    email = jsonObject.getString("email");
                                    diaChi = jsonObject.getString("diaChi");
                                    if(sdtKH.trim().equalsIgnoreCase("")){
                                        list_khachHang.add(new KhachHang(sdt, tenKH, diaChi, email));
                                    }
                                    if(sdtKH.trim().equalsIgnoreCase(sdt)){
                                        list_khachHang_sdt.add(new KhachHang(sdt, tenKH, diaChi, email));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(sdtKH.trim().equalsIgnoreCase("")){
                                listView_khachHang.setAdapter(khachHangAdapter);
                                khachHangAdapter.notifyDataSetChanged();
                            }else {
                                listView_khachHang.setAdapter(khachHangAdapter_sdt);
                                khachHangAdapter_sdt.notifyDataSetChanged();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("============Lỗi rồi===============" + error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", DangNhapActivity.token);
                return headers;
            }
        };
        requestQueue.add(jsonArrayRequest);

    }


    private void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("DANH SÁCH KHÁCH HÀNG");
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
