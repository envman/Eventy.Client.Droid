package com.myleshumphreys.joinin.RetrofitService;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    private String Message;

    private RegisterResponseError ModelState;

    @SerializedName("error")
    private String Error;
}