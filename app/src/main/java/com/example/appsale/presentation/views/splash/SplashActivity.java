package com.example.appsale.presentation.views.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appsale.R;
import com.example.appsale.common.SharePref;
import com.example.appsale.presentation.views.authentications.sign_in.SignInActivity;
import com.example.appsale.presentation.views.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.animation_view);

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                String token = SharePref.getInstance(SplashActivity.this).getToken();
                if (token.isEmpty()) {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
