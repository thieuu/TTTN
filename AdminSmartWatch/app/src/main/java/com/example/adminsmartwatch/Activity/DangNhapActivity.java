package com.example.adminsmartwatch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminsmartwatch.Object.Login;
import com.example.adminsmartwatch.Object.User;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;
import com.example.adminsmartwatch.Until.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangNhapActivity extends AppCompatActivity {
    public static String token;

    Button dangNhap;
    EditText taiKhoan, matKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        controller();
        setEvent();
        actionBar();
    }


    private void controller() {
        dangNhap = findViewById(R.id.button_dangNhap_dangNhap);
        taiKhoan = findViewById(R.id.editText_dangNhap_taiKhoan);
        matKhau = findViewById(R.id.editText_dangNhap_matKhau);
    }

    private void setEvent() {
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                login(intent);

            }

        });
    }


    private void login(Intent intent) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Server.localhost)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        String tk = taiKhoan.getText().toString().trim();
        String mk = matKhau.getText().toString().trim();
        if(tk.equals("")){
            Toast.makeText(getApplicationContext(),"Nhập tài khoản",Toast.LENGTH_SHORT).show();
        }
        if(mk.equals("")){
            Toast.makeText(getApplicationContext(),"Nhập mật khẩu",Toast.LENGTH_SHORT).show();
        }
        if(tk.equals("") == false &&  mk.equals("") == false){
            Login login = new Login(tk,mk);
            Call<User> call = userClient.login(login);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        if(response.body().getRole().equalsIgnoreCase("admin")){
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            token = response.body().getTokenType() + " " +  response.body().getAccessToken();
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Lỗi hệ thống!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void actionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ĐĂNG NHẬP");
    }

}
