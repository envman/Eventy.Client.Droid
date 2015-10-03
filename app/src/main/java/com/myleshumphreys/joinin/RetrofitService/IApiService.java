package com.myleshumphreys.joinin.RetrofitService;

import com.myleshumphreys.joinin.models.Account;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface IApiService {

    @POST("/api/Account/Register")
    Call<ResponseBody> registerAccount(@Body Account account);
}