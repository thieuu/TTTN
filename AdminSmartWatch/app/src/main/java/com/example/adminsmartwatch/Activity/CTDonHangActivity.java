package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.adminsmartwatch.Adapter.CTDonHangAdapter;
import com.example.adminsmartwatch.Object.CTDonHang;
import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTDonHangActivity extends AppCompatActivity {
    TextView textView_ngay, textView_maDH, textView_tenKH, textView_soDT, textView_diaChi, textView_tongCong, textView_email;
    ListView listView_dsDH;

    ArrayList<CTDonHang> list_ctDonHangs;
    CTDonHangAdapter ctDonHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);
        controller();
        setEvent();
        actionBar();
    }

    private void controller() {
        textView_diaChi = findViewById(R.id.textView_chiTietDH_diaChi);
        textView_ngay = findViewById(R.id.textView_chiTietDH_ngay);
        textView_maDH = findViewById(R.id.textView_chiTietDH_maDH);
        textView_tenKH = findViewById(R.id.textView_chiTietDH_tenKH);
        textView_tongCong = findViewById(R.id.textView_chiTietDH_tongCong);
        textView_soDT = findViewById(R.id.textView_chiTietDH_sdt);
        textView_email = findViewById(R.id.textView_chiTietDH_email);
        listView_dsDH = findViewById(R.id.listView_chiTietDH_dsSP);

    }

    private void setEvent() {
        DonHang donHang = getIntent().getParcelableExtra("donhang");
        textView_diaChi.setText(donHang.getDiacChi());
        textView_ngay.setText(donHang.getNgay() + " " + donHang.getGio());
        textView_maDH.setText(donHang.getIdDH() + "");
        textView_tenKH.setText(donHang.getTenKH());
        textView_soDT.setText(donHang.getSdt());
        textView_email.setText(donHang.getEmail());

        list_ctDonHangs = new ArrayList<>();
        ctDonHangAdapter = new CTDonHangAdapter(getApplicationContext(), list_ctDonHangs);
        listView_dsDH.setAdapter(ctDonHangAdapter);
        long tong = 0;
        duLieuSanPham(donHang);
    }

    private void duLieuSanPham(DonHang donHang) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.ctDonHang + "/" + donHang.getIdDH();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            // Khởi tạo biến toàn cục của sản phẩm
                            String tenSP = "", anhSP = "";
                            long giaSP = 0;
                            int soLuong = 0;
                           long tong = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    tenSP = jsonObject.getString("tenSP");
                                    giaSP = jsonObject.getLong("gia");
                                    anhSP = jsonObject.getString("anhSP");
                                    soLuong = jsonObject.getInt("soLuong");
                                    tong += giaSP;
                                    list_ctDonHangs.add(new CTDonHang(tenSP, soLuong, giaSP, anhSP));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ctDonHangAdapter.notifyDataSetChanged();

                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            textView_tongCong.setText(decimalFormat.format(tong) + " Đ");
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
        actionBar.setTitle("CHI TIẾT ĐƠN HÀNG");
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
