package com.myleshumphreys.joinin.RetrofitService;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponseError {

    @SerializedName("model.UserName")
    public List<String> ModelUserName;

    @SerializedName("model.Email")
    public List<String> ModelEmail;

    @SerializedName("model.Password")
    public List<String> ModelPassword;
}