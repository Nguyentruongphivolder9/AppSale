package com.example.appsale.presentation.views.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.Food;
import com.example.appsale.data.repositories.FoodRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private FoodRepository repository;
    private MutableLiveData<AppResource<Food>> foodData = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new FoodRepository();
    }

    public LiveData<AppResource<Food>> getFoods(){
        return foodData;
    }

    public void fetchFoods(){
        foodData.setValue(new AppResource.Loading(null));
        Call<AppResource<Food>> callFoods = repository.fetchFood();
        callFoods.enqueue(new Callback<AppResource<Food>>() {
            @Override
            public void onResponse(Call<AppResource<Food>> call, Response<AppResource<Food>> response) {
                if (response.isSuccessful()) {
                    AppResource<Food> foodResponse = response.body();
                    if (foodResponse.data != null) {
                        foodData.setValue(new AppResource.Success<>(foodResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        foodData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<Food>> call, Throwable t) {
                foodData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
