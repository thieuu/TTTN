package com.example.adminsmartwatch.Until;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.adminsmartwatch.Activity.DangNhapActivity;
import com.example.adminsmartwatch.Adapter.DonHangAdapter;
import com.example.adminsmartwatch.Object.DonHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("unchecked")
public class DuLieuDonHang {
    public static void duLieu(RequestQueue requestQueue, ArrayList<DonHang> list, TextView textView, DonHangAdapter donHangAdapter, String trangThai1) {

        String url = Server.donHang;
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
                                    if (trangThai.equals(trangThai1)) {
                                        list.add(new DonHang(idDH, ngay, gio, sdt, trangThai, diacChi, email, tenKH));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            donHangAdapter.notifyDataSetChanged();
                        }
                        if (list.size() > 0) {
                            textView.setVisibility(View.INVISIBLE);
                        } else {
                            textView.setVisibility(View.VISIBLE);
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

}
