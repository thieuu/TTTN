package com.example.adminsmartwatch.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Object.SanPham;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.RealPathUtil;
import com.example.adminsmartwatch.Until.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuaSpActivity extends AppCompatActivity {
    ImageView hinhSp;
    Button btnChonHinh, btnLuu,btnHuy;
    private static final int MY_REQUEST_CODE = 10;
    EditText edtTenSp,edtGiaSp,edtMotaSp;
    private Spinner spnLoaiSp,spnKieuSp;
    ArrayList<String> list_loai, list_kieu;
    SanPham sanPham;
    int kieu,loai;
    private Uri mUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sp);
        setControl();
        duLieu_kieu_loai(spnKieuSp, true, list_kieu);
        duLieu_kieu_loai(spnLoaiSp, false, list_loai);
        setEvent();
    }
    private void setControl(){
        hinhSp=findViewById(R.id.u_imgsp);
        btnChonHinh=findViewById(R.id.u_btnchonhinh);
        btnHuy=findViewById(R.id.u_btnhuy);
        btnLuu=findViewById(R.id.u_btnluu);
        edtTenSp=findViewById(R.id.u_tensp);
        edtGiaSp=findViewById(R.id.u_giasp);
        edtMotaSp=findViewById(R.id.u_motasp);
        spnLoaiSp=findViewById(R.id.u_loaisp);
        spnKieuSp=findViewById(R.id.u_kieusp);
        list_kieu = new ArrayList<>();
        list_loai = new ArrayList<>();
    }
//    ====

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            hinhSp.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getApplicationContext(), "Permission deny", Toast.LENGTH_LONG).show();
            }
        }


    }
//    ===

    private void suaSp() {
        sanPham = getIntent().getParcelableExtra("dulieusanpham");
        int idSP=sanPham.getIdSP();
        String tenSP = edtTenSp.getText().toString().trim();
        String moTaSP = edtMotaSp.getText().toString().trim();
        String giaSP = edtGiaSp.getText().toString().trim();
        String idLoai = loai + "";
        String idKieu = kieu + "";
        System.out.println("================="+idSP);
//        RequestBody requestTenSP = RequestBody.create(MediaType.parse("multipart/form-data"), tenSP);
//        RequestBody requestGiaSP = RequestBody.create(MediaType.parse("multipart/form-data"), giaSP);
//        RequestBody requestSlTon = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
//        RequestBody requestMotaSP = RequestBody.create(MediaType.parse("multipart/form-data"), moTaSP);
//        RequestBody requestIdLoai = RequestBody.create(MediaType.parse("multipart/form-data"), idLoai);
//        RequestBody requestIdKieu = RequestBody.create(MediaType.parse("multipart/form-data"), idKieu);
//        RequestBody requestAnhSP = RequestBody.create(MediaType.parse("multipart/form-data"), fileName[5]);
        String pathImage = RealPathUtil.getRealPath(this, mUri);
        Log.e("haha",pathImage);
        String[] fileName = pathImage.split("/");

//        ApiServer.apiServer.postSanPham(requestTenSP,requestGiaSP,requestIdKieu,requestIdLoai,requestMotaSP,requestSlTon,requestAnhSP).enqueue(new Callback<PostSanPham>() {
//            @Override
//            public void onResponse(Call<PostSanPham> call, retrofit2.Response<PostSanPham> response) {
//                mProgressDialog.dismiss();
//                PostSanPham sanPham = response.body();
//                if (sanPham.equals("")) {
//                    Toast.makeText(getApplicationContext(), "Inserted!", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<PostSanPham> call, Throwable t) {
//                mProgressDialog.dismiss();
//                Toast.makeText(ThemSpActivity.this,"Post fail",Toast.LENGTH_LONG).show();
//            }
//        });

        String jObjStr = "{\n" +
                "\"idSP\":" + idSP + ",\n" +
                "\"tenSP\":" + "\"" + tenSP + "\",\n" +
                "\"gia\":" + giaSP + ",\n" +
                "\"anhSP\":" + "\"" + fileName[5] + "\",\n" +
                "\"moTa\":" + "\"" + moTaSP + "\",\n" +
                "\"loaiSP\":" + idLoai + ",\n" +
                "\"kieuSP\":" + idKieu + ",\n" +
                "\"soLuongTon\":" + 0 + ",\n" +
                "\"trangThai\":" + "true" + "\n" +
                "}";
        JSONObject jObject = null;
        System.out.println(jObjStr);
        try {
            String url=Server.samPham+"/"+idSP;
            Log.e("haha",url);
            jObject = new JSONObject(jObjStr);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url, jObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Put Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Put Success", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    private void setEvent() {
        sanPham = getIntent().getParcelableExtra("dulieusanpham");
        edtTenSp.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        edtGiaSp.setText(decimalFormat.format(sanPham.getGiaSP()));
        edtMotaSp.setText(sanPham.getMotaSP());
        spnLoaiSp.setSelection(loai - 1);
        spnKieuSp.setSelection(kieu - 1);
        System.out.println(sanPham.getIdLoaiSP());
        Picasso.with(getApplicationContext()).load(sanPham.getHinhSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(hinhSp);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSp = edtTenSp.getText().toString().trim();
                String giaSP = edtGiaSp.getText().toString().trim();
                String motaSP = edtMotaSp.getText().toString().trim();
                if (tenSp.isEmpty() || giaSP.isEmpty() || motaSP.isEmpty()) {
                    Toast.makeText(SuaSpActivity.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                } else {
                    suaSp();
                }
            }
        });
        spnKieuSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kieu = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnLoaiSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }}

//                Dexter.withActivity(SuaSpActivity.this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse response) {
//                                Intent intent=new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                            }
//                        }).check();
//            }
//        });


//        RequestQueue requestQueue=Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
////                hinhSp.setImageResource(R.drawable.ic_launcher_foreground);
//                if (response.trim().equals("success")) {
//                    Toast.makeText(SuaSpActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(SuaSpActivity.this,SanPhamActivity.class));
//                }else{
//                    Toast.makeText(SuaSpActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
//                }
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SuaSpActivity.this,"Xảy ra lỗi",Toast.LENGTH_LONG).show();
//                Log.d("err","Lỗi!\n"+error.toString());
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
////                params.put("anhSP",encodeImageString);
//                params.put("tenSP",edtTenSp.getText().toString().trim());
//                params.put("gia",edtGiaSp.getText().toString().trim());
//                params.put("moTa",edtMotaSp.getText().toString().trim());
//                params.put("idLoai",loai+"");
//                params.put("idKieu",kieu+"");
//                return params;
//            }
////        };
//
//        requestQueue.add(stringRequest);
