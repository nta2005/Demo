package com.nta.annaplay.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofitClient {
    //Tạo biến và cấp phát bộ nhớ:
    private static Retrofit retrofit = null;
    //Function cấu hình Retrofit để tương tác với server:
    public static Retrofit getClient(String base_url){
        // Kiểm tra mạng
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                //Thời gian chờ của việc đọc, ghi, kết nối:
                                .readTimeout(22222, TimeUnit.MILLISECONDS)
                                .writeTimeout(22222, TimeUnit.MILLISECONDS)
                                .connectTimeout(22222, TimeUnit.MILLISECONDS)
                                //Thử kết nối lại khi bị lỗi:
                                .retryOnConnectionFailure(true)
                                //Set giao thức để kết nối không bị lỗi:
                                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                                .build();
        // Chuyển đổi những từ khoá bên API sang từ khoá bên phía Java:
        Gson gson = new GsonBuilder().setLenient().create();
        //Gán vào Retrofit:
        retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return retrofit;
    }
}
