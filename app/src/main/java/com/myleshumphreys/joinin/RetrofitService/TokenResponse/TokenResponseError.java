package com.myleshumphreys.joinin.RetrofitService.TokenResponse;

import com.google.gson.annotations.SerializedName;

public class TokenResponseError {

    @SerializedName("error")
    public String Error;

    @SerializedName("error_description")
    public String ErrorDescription;
}