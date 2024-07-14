package com.example.androidsession.presentation.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidsession.R;
import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.TeamDS;

public class SplashActivity extends AppCompatActivity {
    CityDS cityDS;
    TeamDS teamDS;
    private static final int Splash_Duration = 3000;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        cityDS = new CityDS(this);
        teamDS = new TeamDS(this);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                cityDS.insertCities();
                teamDS.insertTeams();
            }
        },Splash_Duration);
    }
}
