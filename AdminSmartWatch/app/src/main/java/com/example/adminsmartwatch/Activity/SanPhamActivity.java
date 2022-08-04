package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Adapter.SanPhamAdapter;
import com.example.adminsmartwatch.Object.SanPham;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity {

    ListView listView_sanPham;
    ArrayList<SanPham> list_sanPham;
    SanPhamAdapter adapterSanPham;
    Spinner spinner_loai, spinner_kieu;
    ArrayList<String> list_loai, list_kieu;
    ImageButton themSp;
    private String tenLoaiSP, tenKieuSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_sanpham);
        Controller();
        actionBar();
        setEventSpinner();
    }

    private void Controller() {
        list_sanPham = new ArrayList<>();
        listView_sanPham = findViewById(R.id.listView_sanPham);
        adapterSanPham = new SanPhamAdapter(list_sanPham, this);
        listView_sanPham.setAdapter(adapterSanPham);
        themSp=findViewById(R.id.imageButton_dsSanPham_them);
        list_kieu = new ArrayList<>();
        list_loai = new ArrayList<>();
        spinner_kieu = findViewById(R.id.spinner_dsSanPham_kieu);
        spinner_loai = findViewById(R.id.spinner_dsSanPham_loai);
        list_kieu.add("Kiểu");
        list_loai.add("Loại");
    }

    private void setEventSpinner() {
        themSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SanPhamActivity.this,ThemSpActivity.class);
                startActivity(intent);
            }
        });
        duLieu_kieu_loai(spinner_loai, false, list_loai);
        duLieu_kieu_loai(spinner_kieu, true, list_kieu);
        duLieuSanPham("Loại", "Kiểu");
        spinner_loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenLoaiSP = (String) spinner_loai.getItemAtPosition(position);
                if (tenLoaiSP.equals("Loại") || tenLoaiSP.equalsIgnoreCase("Phụ kiện")) {
                    tenKieuSP = "Kiểu";
                    spinner_kieu.setSelection(0);
                    spinner_kieu.setEnabled(false);
                    if (tenLoaiSP.equalsIgnoreCase("Phụ kiện")) {
                        duLieuSanPham(tenLoaiSP, tenKieuSP);
                    } else {
                        duLieuSanPham(tenLoaiSP, tenKieuSP);
                    }
                } else {
                    spinner_kieu.setEnabled(true);
                    duLieuSanPham(tenLoaiSP, tenKieuSP);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_kieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenKieuSP = (String) spinner_kieu.getItemAtPosition(position);
                duLieuSanPham(tenLoaiSP, tenKieuSP);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void duLieuSanPham(String loai_1, String kieu_1) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.samPham;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            list_sanPham.clear();
                            // Khởi tạo biến toàn cục của sản phẩm
                            String tenSP = "", motaSP = "", loaiSP = "", hinhSP = "";
                            int idSP = 0, giaSP = 0, soLuongSP = 0;
                            boolean trangThai;

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    idSP = jsonObject.getInt("idSP");
                                    tenSP = jsonObject.getString("tenSP");
                                    giaSP = jsonObject.getInt("gia");
                                    motaSP = jsonObject.getString("moTa");
                                    JSONObject loai = jsonObject.getJSONObject("loaiSP");
                                    int idLoaiSP = loai.getInt("idLoaiSP");
                                    loaiSP = loai.getString("tenLoai");
                                    JSONObject kieuSP = jsonObject.getJSONObject("kieuSP");
                                    int idKieuSP = kieuSP.getInt("idKieusp");
                                    String tenKieu = kieuSP.getString("tenKieu");
                                    hinhSP = jsonObject.getString("anhSP");
                                    soLuongSP = jsonObject.getInt("soLuongTon");
                                    trangThai = jsonObject.getBoolean("trangThai");
                                    if (loai_1.equals("Loại")) {
                                        list_sanPham.add(new SanPham(idSP, tenSP, giaSP, motaSP, idLoaiSP, loaiSP, idKieuSP, tenKieu, hinhSP, soLuongSP, trangThai));
                                    } else {
                                        if (kieu_1.equals("Kiểu")) {
                                            if (loaiSP.equals(loai_1)) {
                                                list_sanPham.add(new SanPham(idSP, tenSP, giaSP, motaSP, idLoaiSP, loaiSP, idKieuSP, tenKieu, hinhSP, soLuongSP, trangThai));
                                            }
                                        } else {
                                            if (loaiSP.equals(loai_1) && tenKieu.equals(kieu_1)) {
                                                list_sanPham.add(new SanPham(idSP, tenSP, giaSP, motaSP, idLoaiSP, loaiSP, idKieuSP, tenKieu, hinhSP, soLuongSP, trangThai));
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapterSanPham.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("============Lỗi rồi===============" + error);

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void duLieu_kieu_loai(Spinner spinner, boolean kieu_loai, ArrayList<String> list) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url;
        String id, ten;
        if (kieu_loai == true) {
            url = Server.kieu;
            ten = "tenKieu";
        } else {
            url = Server.loai;
            ten = "tenLoai";
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            // Khởi tạo biến toàn cục của sản phẩm
                            String id1 = "", ten1 = "";

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ten1 = jsonObject.getString(ten);
                                    list.add(ten1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                            spinner.setAdapter(arrayAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("============Lỗi rồi===============" + error);

            }
        }) {
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
        actionBar.setTitle("DANH SÁCH SẢN PHẨM");
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
