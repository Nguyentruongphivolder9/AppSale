package com.example.appsale.presentation.views.authentications.sign_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.User;
import com.example.appsale.data.repositories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInViewModel extends ViewModel {
    private AuthenticationRepository repository;
    private MutableLiveData<AppResource<User>> userData = new MutableLiveData<>();

    public SignInViewModel() {
        repository = new AuthenticationRepository();
    }

    public LiveData<AppResource<User>> getUserData(){
        return userData;
    }

    public void login(String email, String password){
        userData.setValue(new AppResource.Loading(null));
        Call<AppResource<User>> callLogin = repository.login(email, password);
        callLogin.enqueue(new Callback<AppResource<User>>() {
            @Override
            public void onResponse(Call<AppResource<User>> call, Response<AppResource<User>> response) {
                if (response.isSuccessful()) {
                    AppResource<User> userResponse = response.body();
                    if (userResponse.data != null) {
                        userData.setValue(new AppResource.Success<User>(userResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        userData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<User>> call, Throwable t) {
                userData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
