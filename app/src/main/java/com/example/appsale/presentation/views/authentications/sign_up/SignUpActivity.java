package com.example.appsale.presentation.views.authentications.sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appsale.R;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.User;
import com.example.appsale.presentation.views.authentications.sign_in.SignInActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText inputName, inputEmail, inputPassword, inputPhone, inputAddress;
    LinearLayout signUp;
    LinearLayout layoutLoading;
    SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputEmail = findViewById(R.id.textEditEmail);
        inputName = findViewById(R.id.textEditName);
        inputPhone = findViewById(R.id.textEditPhone);
        inputAddress = findViewById(R.id.textEditLocation);
        inputPassword = findViewById(R.id.textEditPassword);
        signUp = findViewById(R.id.sign_up);
        layoutLoading = findViewById(R.id.layout_loading);
        
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        event();
    }

    private void event() {
        signUpViewModel.getUserData().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status){
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        finish();
                        break;
                    case ERROR:
                        Toast.makeText(SignUpActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String phone = inputPhone.getText().toString();
                String name = inputName.getText().toString();
                String address = inputAddress.getText().toString();

                if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Bạn chưa truyền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUpViewModel.register(email,password,phone,name,address);
            }
        });
    }
}
