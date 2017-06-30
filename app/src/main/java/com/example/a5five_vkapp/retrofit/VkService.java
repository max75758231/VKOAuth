package com.example.a5five_vkapp.retrofit;

import com.example.a5five_vkapp.data.ResponseData;
import com.example.a5five_vkapp.data.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VkService {

    @GET("method/users.get")
    Call<ResponseData<List<UserData>>> getUserInfo(@Query("access_token") String accessToken,
                                                   @Query("fields") String fields);
}
