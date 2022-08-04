package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.adminsmartwatch.Adapter.PhieuNhapAdapter;
import com.example.adminsmartwatch.Object.PhieuNhap;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Danhsach_PhieunhapActivity extends AppCompatActivity {
ListView lvDanhSach;
PhieuNhapAdapter phieuNhapAdapter;
ArrayList<PhieuNhap> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach__phieunhap);
        actionBar();
        lvDanhSach=findViewById(R.id.lvDanhSach);

        list = new ArrayList<>();
        phieuNhapAdapter = new PhieuNhapAdapter(this, list);
        lvDanhSach.setAdapter(phieuNhapAdapter);
        setEvent();
    }

    private void setEvent() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.phieuNhap;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            // Khởi tạo biến toàn cục của sản phẩm
                            String id = "", ngay = "";
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getString("idPN");
                                    ngay = jsonObject.getString("ngayNhap");

                                    list.add(new PhieuNhap(id,ngay));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            phieuNhapAdapter.notifyDataSetChanged();
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
        actionBar.setTitle("DANH SÁCH PHIẾU NHẬP");
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