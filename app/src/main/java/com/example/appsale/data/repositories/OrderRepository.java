package com.example.appsale.data.repositories;

import android.content.Context;

import com.example.appsale.data.datasources.remote.ApiService;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.datasources.remote.RetrofitClient;
import com.example.appsale.data.models.Order;


import java.util.HashMap;

import retrofit2.Call;

public class OrderRepository {
    private ApiService apiService;

    public OrderRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<Order>> addToCart(String idFood) {
        HashMap<String, String> body = new HashMap<>();
        body.put("id_product", idFood);
        return apiService.addToCart(body);
    }
}
