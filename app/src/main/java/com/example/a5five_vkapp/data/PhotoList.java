package com.example.a5five_vkapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PhotoList {

    @SerializedName("items")
    private ArrayList<PhotoDataModel> photoDataModels = new ArrayList<>();

    public ArrayList<PhotoDataModel> getPhotoDataModels() {
        return photoDataModels;
    }
}
