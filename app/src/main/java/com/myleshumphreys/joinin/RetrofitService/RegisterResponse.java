package com.myleshumphreys.joinin.RetrofitService;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("access_token")
    public String AccessToken;

    public String Message;

    public RegisterResponseError ModelState;

    @SerializedName("error")
    public String Error;
}