package com.example.adminsmartwatch.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Activity.CTDonHangActivity;
import com.example.adminsmartwatch.Activity.DangNhapActivity;
import com.example.adminsmartwatch.Activity.Fragment.ChoDuyetFragment;
import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DonHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonHang> list;

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
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
        if (donHang.trangThai.equals("Chờ duyệt") == false) {
            button_duyetDon.setVisibility(View.GONE);
            button_huyDon.setVisibility(View.GONE);
        }

        button_duyetDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete("XÁC NHẬN ĐƠN HÀNG","Xác nhận giao đơn hàng!",donHang,"Đang giao", list,position);
            }
        });

        button_huyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete("HỦY ĐƠN HÀNG","Xác nhận hủy đơn hàng!",donHang,"Đã hủy", list,position);
            }
        });

        linearLayout_donHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CTDonHangActivity.class);
                intent.putExtra("donhang", donHang);
                context.startActivity(intent);
            }
        });


        return row;
    }

    private void delete(String title, String message,DonHang donHang, String trangthai, ArrayList<DonHang> list, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(trangthai.equals("Đã hủy")){
            builder.setIcon(R.drawable.ic_baseline_clear);
        }else {
            builder.setIcon(R.drawable.ic_baseline_check_box_24);
        }

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDonHang(donHang,trangthai);
                list.remove(position);
                notifyDataSetChanged();
                if(list.size() == 0){
                    ChoDuyetFragment.textView_choDuyet.setVisibility(View.VISIBLE);
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void updateDonHang(DonHang donHang, String trangthai) {
        String jObjStr = "{\n" +
                "\"idDH\": " + donHang.idDH + ",\n" +
                "\"ngayDatHang\": " + "\"" + donHang.ngay + " " + donHang.gio + "\"" + ",\n" +
                "\"sdt\": " + "\"" + donHang.sdt + "\"" + ",\n" +
                "\"trangThai\": " + "\"" + trangthai + "\"" +
                "}";
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(jObjStr);
            String url = Server.donHang + "/" + donHang.idDH;
            RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, jObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("jsonResponse", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error volley: ", error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", DangNhapActivity.token);
                    return headers;
                }
            };
            queue.add(stringRequest);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
