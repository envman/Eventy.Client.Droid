package com.myleshumphreys.joinin.ApiMethods;

import com.myleshumphreys.joinin.models.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface IApiService {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

    @POST("/api/Account/Register")
    Call<User> registerUser(@Body User user);
}