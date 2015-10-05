package com.myleshumphreys.joinin.RetrofitService;

import com.myleshumphreys.joinin.models.Account;
import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface IApiService {

    @POST("/api/Account/Register")
    Call<ResponseBody> registerAccount(@Body Account account);

    @FormUrlEncoded
    @POST("/Token")
    Call<ResponseBody> getToken(@FieldMap Map<String, String> grantType,
                                    @FieldMap Map<String, String> username,
                                    @FieldMap Map<String, String> password);
}