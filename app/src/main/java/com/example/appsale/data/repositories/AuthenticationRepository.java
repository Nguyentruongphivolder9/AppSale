package com.example.appsale.data.repositories;

import com.example.appsale.data.datasources.remote.ApiService;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.User;

import retrofit2.Call;

public class AuthenticationRepository {
    private ApiService apiService;

    public Call<AppResource<User>> login(String email, String password){
        return apiService.login(new User(email,password));
    }

}
