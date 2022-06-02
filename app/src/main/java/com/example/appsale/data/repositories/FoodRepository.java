package com.example.appsale.data.repositories;

import com.example.appsale.data.datasources.remote.ApiService;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.datasources.remote.RetrofitClient;
import com.example.appsale.data.models.Food;
import com.example.appsale.data.models.User;

import retrofit2.Call;

public class FoodRepository {
    private ApiService apiService;

    public FoodRepository() { apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<AppResource<Food>> fetchFood() {
        return apiService.fetchFoods();
    }
}
