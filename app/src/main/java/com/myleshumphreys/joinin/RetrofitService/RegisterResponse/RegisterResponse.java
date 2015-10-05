package com.myleshumphreys.joinin.RetrofitService.RegisterResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    public String Message;

    @SerializedName("error")
    public String Error;

    public RegisterResponseError ModelState;
}