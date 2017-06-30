package com.example.a5five_vkapp;


import android.util.Log;

import com.example.a5five_vkapp.data.ResponseData;
import com.example.a5five_vkapp.data.UserData;
import com.example.a5five_vkapp.retrofit.VkService;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public final static String CLIENT_ID = "5814796";
    public final static String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    public final static String FIELDS = "photo_max";

    public final static String AUTH_URL = "https://oauth.vk.com/authorize?client_id=" + CLIENT_ID +
            "&redirect_uri=" + REDIRECT_URI + "&display=mobile&scope=photos&response_type=token";

    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final VkService vkService;


    private String accessToken;
    private long userId;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    private static RetrofitClient instanse;

    public static RetrofitClient getInstanse() {
        if (instanse == null) {
            instanse = new RetrofitClient();
        }
        return instanse;
    }

    private RetrofitClient() {
        okHttpClient = buildOkHttpClient();
        retrofit = buildRetrofit();
        vkService = retrofit.create(VkService.class);
    }

    private OkHttpClient buildOkHttpClient() {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(interceptor);
        }
        return okHttpClientBuilder.build();
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.vk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
