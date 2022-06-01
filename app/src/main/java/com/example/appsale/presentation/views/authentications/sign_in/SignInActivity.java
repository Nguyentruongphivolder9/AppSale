package com.example.appsale.presentation.views.authentications.sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appsale.R;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.User;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText inputEdtEmail , inputEdtPassword;
    LinearLayout signIn;
    SignInViewModel signInViewModel;
    LinearLayout layoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolbar = findViewById(R.id.toolbarLogin);
        inputEdtEmail = findViewById(R.id.textEditEmail);
        inputEdtPassword = findViewById(R.id.textEditPassword);
        signIn = findViewById(R.id.sign_in);
        layoutLoading = findViewById(R.id.layout_loading);

        initView();

        event();
    }

    private void initView() {
        setStatusBar();

        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    private void event() {

        // Observer data
        signInViewModel.getUserData().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status){
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        Toast.makeText(SignInActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEdtEmail.getText().toString();
                String password = inputEdtPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Người dùng chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                signInViewModel.login(email, password);
            }
        });
    }

    private void setStatusBar() {
        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.blue));
    }
}
