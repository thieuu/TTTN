package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Adapter.DonHang_KhachHangAdapter;
import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTKhachHangActivity extends AppCompatActivity {
    TextView textView_tenKH, textView_soDT, textView_diaChi,textView_email;
    ListView listView_dsDH;

    ArrayList<DonHang> list_donHangs;
    DonHang_KhachHangAdapter donHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_khachhang);
        controller();
        setEvent();
        actionBar();
    }

    private void controller() {
        textView_diaChi = findViewById(R.id.textView_chiTietKH_diaChi);
        textView_tenKH = findViewById(R.id.textView_chiTietKH_tenKH);
        textView_soDT = findViewById(R.id.textView_chiTietKH_sdt);
        textView_email = findViewById(R.id.textView_chiTietKH_email);
        listView_dsDH = findViewById(R.id.listView_chiTietKH_dsDH);

    }

    private void setEvent() {
        textView_diaChi.setText(getIntent().getStringExtra("diaChi"));
        textView_tenKH.setText(getIntent().getStringExtra("ten"));
        textView_soDT.setText(getIntent().getStringExtra("sdt"));
        textView_email.setText(getIntent().getStringExtra("email"));

        list_donHangs = new ArrayList<>();
        donHangAdapter = new DonHang_KhachHangAdapter(getApplicationContext(), list_donHangs);
        listView_dsDH.setAdapter(donHangAdapter);
        duLieuDonHang(getIntent().getStringExtra("sdt"));

        listView_dsDH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CTDonHangActivity.class);
                intent.putExtra("donhang", list_donHangs.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void duLieuDonHang(String sdt) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.donHang + "/sdt/" + sdt;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            // Khởi tạo biến toàn cục của sản phẩm
                            String ngay = "", gio = "", sdt = "", trangThai = "", diacChi = "", email = "", tenKH = "";
                            int idDH = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    JSONObject donhang = jsonObject.getJSONObject("donhang");
                                    idDH = donhang.getInt("idDH");
                                    sdt = donhang.getString("sdt");
                                    trangThai = donhang.getString("trangThai");
                                    String ngayDatHang = donhang.getString("ngayDatHang");
                                    char[] tamp = new char[10];
                                    ngayDatHang.getChars(0, 10, tamp, 0);
                                    ngay = String.copyValueOf(tamp).trim();
                                    char[] tamp2 = new char[8];
                                    ngayDatHang.getChars(11, 19, tamp2, 0);
                                    gio = String.copyValueOf(tamp2).trim();
                                    diacChi = jsonObject.getString("diacChi");
                                    email = jsonObject.getString("email");
                                    tenKH = jsonObject.getString("tenKH");
                                    list_donHangs.add(new DonHang(idDH, ngay, gio, sdt, trangThai, diacChi, email, tenKH));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            donHangAdapter.notifyDataSetChanged();
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
        actionBar.setTitle("CHI TIẾT KHÁCH HÀNG");
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
