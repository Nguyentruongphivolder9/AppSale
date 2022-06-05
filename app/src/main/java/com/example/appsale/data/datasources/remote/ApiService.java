package com.example.appsale.data.datasources.remote;

import com.example.appsale.data.models.Food;
import com.example.appsale.data.models.Order;
import com.example.appsale.data.models.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
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

    @POST("product")
    Call<AppResource<Order>> addToCart(@Body HashMap<String, String> body);

}
