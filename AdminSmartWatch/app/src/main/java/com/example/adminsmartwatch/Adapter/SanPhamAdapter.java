package com.example.adminsmartwatch.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Activity.ChiTietSanPhamActivity;
import com.example.adminsmartwatch.Activity.DangNhapActivity;
import com.example.adminsmartwatch.Activity.SuaSpActivity;
import com.example.adminsmartwatch.Object.SanPham;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamAdapter extends BaseAdapter {
    ArrayList<SanPham> arrayListSP;
    Context context;


    public SanPhamAdapter(ArrayList<SanPham> arrayListSP, Context context) {
        this.arrayListSP = arrayListSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_sanpham, null);
        ImageView image_sanPham = row.findViewById(R.id.image_sanPham_hinhSP);
        TextView textView_tenSP = row.findViewById(R.id.textView_sanPham_tenSP);
        TextView textView_giaSP = row.findViewById(R.id.textView_sanPham_giaSP);
        Button button_sua = row.findViewById(R.id.button_sanPham_sua);
        Button button_xoa = row.findViewById(R.id.button_sanPham_xoa);
        LinearLayout linearLayout_sanPham = row.findViewById(R.id.linearLayout_row_sanPham);

        SanPham sanPham = arrayListSP.get(position);
        textView_tenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textView_giaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) + " Đ");
        Picasso.with(context).load(sanPham.getHinhSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(image_sanPham);

        linearLayout_sanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("sanpham", sanPham);
                context.startActivity(intent);
            }
        });
        button_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), SuaSpActivity.class);
                intent.putExtra("dulieusanpham",sanPham);
                context.startActivity(intent);
            }
        });
        button_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                delete(requestQueue, "XÓA MẶT HÀNG", "Xác nhận xóa sản phẩm!", arrayListSP, position, sanPham);
            }
        });

        return row;
    }

    private void delete(RequestQueue requestQueue, String title, String message, ArrayList<SanPham> list, int position, SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = Server.xoaSamPham + "/" + sanPham.idSP;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                        url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
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
                requestQueue.add(jsonObjectRequest);
                list.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
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

}
