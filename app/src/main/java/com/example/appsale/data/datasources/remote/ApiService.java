package com.example.appsale.data.datasources.remote;

import com.example.appsale.data.models.Food;
import com.example.appsale.data.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/sign-in")
    Call<AppResource<User>> login(@Body User user);

    @POST("user/sign-up")
    Call<AppResource<User>> register(@Body User user);

    @GET("product")
    Call<AppResource<List<Food>>> fetchFoods();

}
