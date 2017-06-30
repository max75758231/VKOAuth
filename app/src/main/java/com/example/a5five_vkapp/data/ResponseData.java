package com.example.a5five_vkapp.data;

import com.google.gson.annotations.SerializedName;

public class ResponseData<Data> {

    @SerializedName("response")
    private Data data;

    public Data getData() {
        return data;
    }

}
