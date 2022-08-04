package com.example.adminsmartwatch.Until;
import com.example.adminsmartwatch.Object.PostSanPham;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServer {
    //public static final String DOMAIN="http://192.168.1.10:9009/";
    public static final String DOMAIN="https://springbootapi-heroku.herokuapp.com/";

    Gson gson=new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
    ApiServer apiServer=new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServer.class);

@Multipart
@POST("sanpham")
    Call<PostSanPham> postSanPham(@Part(Constant.KEY_TENSP) RequestBody tenSP,
                                  @Part(Constant.KEY_GIASP) RequestBody gia,
                                  @Part(Constant.KEY_IDKIEU) RequestBody idKieu,
                                  @Part(Constant.KEY_IDLOAI) RequestBody idLoai,
                                  @Part(Constant.KEY_MOTASP) RequestBody moTa,
                                  @Part(Constant.KEY_SLTONSP) RequestBody soLuongTon,
                                  @Part (Constant.KEY_ANHSP)  RequestBody anhSP);
}
