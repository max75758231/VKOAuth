package com.example.a5five_vkapp.retrofit;

import com.example.a5five_vkapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String CLIENT_ID = "5814796";
    public static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    public static final String FIELD = "photo_max";

    public static final String AUTH_URL = "https://oauth.vk.com/authorize?client_id=" + CLIENT_ID +
            "&redirect_uri=" + REDIRECT_URI + "&display=mobile&scope=photos&response_type=token";

    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final VkService vkService;

    private static final String API_SERVICE_ADDRESS = "https://api.vk.com/";

    private String accessToken;
    private long userId;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private static RetrofitClient instance;

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
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
                .baseUrl(API_SERVICE_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
