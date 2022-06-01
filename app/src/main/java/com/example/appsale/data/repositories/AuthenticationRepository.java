package com.example.appsale.data.repositories;

import com.example.appsale.data.datasources.remote.ApiService;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.datasources.remote.RetrofitClient;
import com.example.appsale.data.models.User;

import retrofit2.Call;

public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository() { apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<AppResource<User>> login(String email, String password){
        return apiService.login(new User(email,password));
    }

    public Call<AppResource<User>> register(String email, String password, String name, String phone, String address) {
        return apiService.register(new User(email, name, phone, address, password));
    }
}
