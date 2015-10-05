package com.myleshumphreys.joinin.RetrofitService.TokenResponse;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("access_token")
    public String AccessToken;

    @SerializedName("token_type")
    public String TokenType;

    @SerializedName("expires_in")
    public int TokenExpiresIn;

    @SerializedName("userName")
    public String TokenUserName;

    @SerializedName(".issued")
    public String TokenIssued;

    @SerializedName(".expires")
    public String TokenExpires;
}
