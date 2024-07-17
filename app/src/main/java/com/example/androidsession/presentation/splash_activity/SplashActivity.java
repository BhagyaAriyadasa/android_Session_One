package com.example.androidsession.presentation.splash_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.presentation.register_activity.RegisterActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    SplashActivityViewModel viewModel;
    private static final int Splash_Duration = 3000;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        viewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        viewInit();
    }

    private void viewInit() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadInitDataFromDB();
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        },Splash_Duration);
    }

    private void loadInitDataFromDB(){
        viewModel.saveCities(this);
        viewModel.saveTeams(this);
    }
}
