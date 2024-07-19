package com.example.androidsession.presentation.splash_activity;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.ProfileDS;
import com.example.androidsession.database.data_service.TeamDS;
import com.example.androidsession.database.table_entity.ProfileEntity;
import com.example.androidsession.http.ResponseCall;
import com.example.androidsession.http.services.GetHS;
import com.example.androidsession.http.services.PostHS;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;

public class SplashActivityViewModel extends ViewModel {

    public void callPostAPI(Context context) throws JSONException {
        new ProfileDS(context).getAll().forEach(profileEntity -> {
            new PostHS(profileEntity).execute(new ResponseCall<List<ProfileEntity>>() {
                @Override
                public void onSuccess(List<ProfileEntity> data) {
                    if(data != null && !data.isEmpty()){
                        data.forEach(profileEntity -> new ProfileDS(context).createOrUpdate(profileEntity));
                        System.out.println("Json body : " + new Gson().toJson(data));
                    }
                }

                @Override
                public void onError(String Error) {

                }
            });
        });

    }

    public void callGetAPI(Context context){
        new GetHS().execute(new ResponseCall<List<ProfileEntity>>() {
            @Override
            public void onSuccess(List<ProfileEntity> data) {
                if(data != null && !data.isEmpty()){
                    data.forEach(profileEntity -> new ProfileDS(context).createOrUpdate(profileEntity)
                    );
                    System.out.println("json body : " + new Gson().toJson(data));
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void saveCities(Context context){
        new CityDS(context).insertCities();
    }

    public void saveTeams(Context context){
        new TeamDS(context).insertTeams();
    }

}
