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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private FoodRepository repository;
    private MutableLiveData<AppResource<List<Food>>> foodsData = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new FoodRepository();
    }

    public LiveData<AppResource<List<Food>>> getFoods(){
        return foodsData;
    }

    public void fetchFoods(){
        foodsData.setValue(new AppResource.Loading(null));
        Call<AppResource<List<Food>>> callFoods = repository.fetchFood();
        callFoods.enqueue(new Callback<AppResource<List<Food>>>() {
            @Override
            public void onResponse(Call<AppResource<List<Food>>> call, Response<AppResource<List<Food>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<Food>> foodResponse = response.body();
                    if (foodResponse.data != null) {
                        foodsData.setValue(new AppResource.Success<>(foodResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        foodsData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<Food>>> call, Throwable t) {
                foodsData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
