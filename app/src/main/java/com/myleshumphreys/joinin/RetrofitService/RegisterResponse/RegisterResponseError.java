package com.myleshumphreys.joinin.RetrofitService.RegisterResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponseError {

    @SerializedName("model.UserName")
    private List<String> ModelUserName;

    @SerializedName("model.Email")
    private List<String> ModelEmail;

    @SerializedName("model.Password")
    private List<String> ModelPassword;
}