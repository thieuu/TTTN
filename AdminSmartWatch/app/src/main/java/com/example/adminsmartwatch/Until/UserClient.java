package com.example.adminsmartwatch.Until;


import com.example.adminsmartwatch.Object.DonHang;
import com.example.adminsmartwatch.Object.Login;
import com.example.adminsmartwatch.Object.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/login")
    Call<User> login(@Body Login login);

    @GET("/donhang")
    Call<List<DonHang>> getDonHang(@Header("Authorization") String authToken);

}
