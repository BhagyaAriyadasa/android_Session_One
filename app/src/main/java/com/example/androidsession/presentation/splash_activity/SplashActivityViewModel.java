package com.example.androidsession.presentation.splash_activity;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.TeamDS;

public class SplashActivityViewModel extends ViewModel {

    public void saveCities(Context context){
        new CityDS(context).insertCities();
    }

    public void saveTeams(Context context){
        new TeamDS(context).insertTeams();
    }

}
