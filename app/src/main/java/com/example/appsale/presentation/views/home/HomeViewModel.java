package com.example.appsale.presentation.views.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.Food;
import com.example.appsale.data.models.Order;
import com.example.appsale.data.repositories.FoodRepository;
import com.example.appsale.data.repositories.OrderRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private OrderRepository orderRepository;
    private MutableLiveData<AppResource<List<Food>>> foodData = new MutableLiveData<>();
    private MutableLiveData<AppResource<Order>> orderData = new MutableLiveData<>();


    public HomeViewModel(Context context) {
        foodRepository = new FoodRepository(context);
        orderRepository = new OrderRepository(context);
    }

    public LiveData<AppResource<List<Food>>> getFoods(){
        return foodData;
    }

    public LiveData<AppResource<Order>> getOrder(){
        return orderData;
    }

    public void fetchFoods(){
        foodData.setValue(new AppResource.Loading(null));
        Call<AppResource<List<Food>>> callFoods = foodRepository.fetchFood();
        callFoods.enqueue(new Callback<AppResource<List<Food>>>() {
            @Override
            public void onResponse(Call<AppResource<List<Food>>> call, Response<AppResource<List<Food>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<Food>> foodResponse = response.body();
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
            public void onFailure(Call<AppResource<List<Food>>> call, Throwable t) {
                foodData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void fetchOrder(String idFood){
        orderData.setValue(new AppResource.Loading(null));
        Call<AppResource<Order>> callOrder = orderRepository.addToCart(idFood);
        callOrder.enqueue(new Callback<AppResource<Order>>() {
            @Override
            public void onResponse(Call<AppResource<Order>> call, Response<AppResource<Order>> response) {
                if (response.isSuccessful()) {
                    AppResource<Order> orderResponse = response.body();
                    if (orderResponse.data != null) {
                        orderData.setValue(new AppResource.Success<>(orderResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        orderData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<Order>> call, Throwable t) {
                orderData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
