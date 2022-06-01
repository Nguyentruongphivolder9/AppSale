package com.example.appsale.data.datasources.remote;

import com.example.appsale.data.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/sign-in")
    Call<AppResource<User>> login(@Body User user);
}
