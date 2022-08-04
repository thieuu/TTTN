package com.example.adminsmartwatch.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.RealPathUtil;
import com.example.adminsmartwatch.Until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ThemSpActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    public static final String TAG = ThemSpActivity.class.getName();
    ImageView hinhSp;
    Button btnLuu, btnHuy, btnUpload;
    EditText edtTenSp, edtGiaSp, edtMotaSp;
    Spinner spnLoaiSp, spnKieuSp;
    ArrayList<String> list_loai, list_kieu;
    int loai, kieu;
    private Uri mUri;
    private static final String url = Server.samPham;
    private ProgressDialog mProgressDialog;
//    =======================================

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

    //=======================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sp);
        setControl();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");

        duLieu_kieu_loai(spnKieuSp, true, list_kieu);
        duLieu_kieu_loai(spnLoaiSp, false, list_loai);
        setEvent();

    }

    private void setControl() {
        hinhSp = findViewById(R.id.i_imgsp);
        btnHuy = findViewById(R.id.i_btnhuy);
        btnLuu = findViewById(R.id.i_btnluu);
        edtTenSp = findViewById(R.id.i_tensp);
        edtGiaSp = findViewById(R.id.i_giasp);
        edtMotaSp = findViewById(R.id.i_motasp);
        spnLoaiSp = findViewById(R.id.i_loaisp);
        spnKieuSp = findViewById(R.id.i_kieusp);
        btnUpload = findViewById(R.id.i_btnupload);
        list_kieu = new ArrayList<>();
        list_loai = new ArrayList<>();

    }

    private void setEvent() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    callApiPostSP();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }

    private void callApiPostSP() {


        String tenSP = edtTenSp.getText().toString().trim();
        RequestBody requestTenSP = RequestBody.create(MediaType.parse("multipart/form-data"), tenSP);
        String giaSP = edtGiaSp.getText().toString().trim();
        RequestBody requestGiaSP = RequestBody.create(MediaType.parse("multipart/form-data"), giaSP);
        String moTaSP = edtMotaSp.getText().toString().trim();
        RequestBody requestSlTon = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody requestMotaSP = RequestBody.create(MediaType.parse("multipart/form-data"), moTaSP);
        String idLoai = loai + "";
        RequestBody requestIdLoai = RequestBody.create(MediaType.parse("multipart/form-data"), idLoai);
        String idKieu = kieu + "";
        RequestBody requestIdKieu = RequestBody.create(MediaType.parse("multipart/form-data"), idKieu);
        String pathImage = RealPathUtil.getRealPath(this, mUri);
        String[] fileName = pathImage.split("/");
        if(tenSP.equals("")){
            Toast.makeText(getApplicationContext(), "Nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        if(giaSP.equals("")){
            Toast.makeText(getApplicationContext(), "Nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(!tenSP.equals("") && !giaSP.equals("")){
//            mProgressDialog.show();
//        }
        Log.e("idKieu", idKieu);
        Log.e("idLoai", idLoai);
        Log.e("moTaSP", moTaSP);
        Log.e("giaSP", giaSP);
        Log.e("tenSP", tenSP);
        Log.e("fileName[5]", fileName[5]);
        RequestBody requestAnhSP = RequestBody.create(MediaType.parse("multipart/form-data"), fileName[5]);
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
                "\"tenSP\":" + "\"" + tenSP + "\",\n" +
                "\"gia\":" + giaSP + ",\n" +
                "\"anhSP\":" + "\"" + fileName[5] + "\",\n" +
                "\"moTa\":" + "\"" + moTaSP + "\",\n" +
                "\"loaiSP\":" +  idLoai + ",\n" +
                "\"kieuSP\":" + idKieu + ",\n" +
                "\"soLuongTon\":" + 0 + "\n" +
                "}";
        JSONObject jObject = null;
        System.out.println(jObjStr);
        try {
            jObject = new JSONObject(jObjStr);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            mProgressDialog.dismiss();
                            Intent intent=new Intent(ThemSpActivity.this,SanPhamActivity.class);
                            startActivity(intent);
//                            Toast.makeText(getApplicationContext(), "Post Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                    Intent intent=new Intent(ThemSpActivity.this,SanPhamActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Post Success", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    =======================================

//    private void themSp(String url1) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String moTa = "";
//        if (edtMotaSp.getText().toString().trim() == "") {
//            moTa = "\"\"";
//        } else {
//            moTa = edtMotaSp.getText().toString().trim();
//        }
//        String arr = "{\n" +
//                "\"tenSP\":" + edtTenSp.getText().toString().trim() + ",\n" +
//                "\"gia\":" + edtGiaSp.getText().toString().trim() + ",\n" +
//                "\"anhSP\":" + "" + ",\n" +
//                "\"moTa\":" + moTa + " ,\n" +
//                "\"loaiSP\":" + loai + ",\n" +
//                "\"kieuSP\":" + kieu + "\n" +
//                "}";
//
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(arr);
//            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url1, jsonObject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    if (response != null) {
//                        hinhSp.setImageResource(R.drawable.ic_launcher_foreground);
//                        Toast.makeText(ThemSpActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(ThemSpActivity.this, SanPhamActivity.class));
//                    } else {
//                        Toast.makeText(ThemSpActivity.this, "Thêm thất bại", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(ThemSpActivity.this, "Xảy ra lỗi", Toast.LENGTH_LONG).show();
//                    Log.d("err", "Lỗi!\n" + error.toString());
//                }
//
//            });
//
//            requestQueue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ;
//        System.out.println("Hết rồi nè");
//    }


        private void duLieu_kieu_loai (Spinner spinner,boolean kieu_loai, ArrayList<String > list){
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
    }