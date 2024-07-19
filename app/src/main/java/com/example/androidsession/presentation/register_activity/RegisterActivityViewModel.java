package com.example.androidsession.presentation.register_activity;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.data_service.ProfileDS;
import com.example.androidsession.database.data_service.TeamDS;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.LoginEntity;
import com.example.androidsession.database.table_entity.ProfileEntity;
import com.example.androidsession.database.table_entity.TeamEntity;

import org.json.JSONException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivityViewModel extends ViewModel {

    private MutableLiveData<List<CityEntity>> cityData;
    private MutableLiveData<List<TeamEntity>> teamData;

    public RegisterActivityViewModel(){
        cityData = new MutableLiveData<>();
        teamData = new MutableLiveData<>();
    }

    public LiveData<List<CityEntity>> getCities(){
        return cityData;
    }

    public LiveData<List<TeamEntity>> getTeams(){
        return teamData;
    }

    public void fetchCities(Context context){
        CityDS cityDS = new CityDS(context);
        try {
            List<CityEntity> cities = cityDS.getCityUIDAndName();
            cityData.postValue(cities);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void fetchTeams(Context context){
        TeamDS teamDS = new TeamDS(context);
        try{
            List<TeamEntity> teams = teamDS.getTeamUIDAndName();
            teamData.postValue(teams);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void postRegister(Context context, String name, int age, int city, int team, double salary, int loginUid){
        saveProfile(context, name, age, city, team, salary, loginUid);
    }

    public void postLogin(Context context, String userName, String password, boolean isActive){
        saveLogin(context,userName,password,isActive);
    }

    private void saveProfile(Context context, String name, int age, int city, int team, double salary, int loginUid){
        ProfileEntity entity = new ProfileEntity();
        entity.setName(name);
        entity.setAge(age);
        entity.setTeamUid(team);
        entity.setCityUid(city);
        entity.setSalary(salary);
        entity.setLoginUid(loginUid);
        new ProfileDS(context).createOrUpdate(entity);
    }

    private void saveLogin(Context context, String userName, String password, boolean isActive){
        LoginEntity entity = new LoginEntity();

        entity.setUserName(userName);
        entity.setPassword(password);
        entity.setIsActive(isActive);

        new LoginDS(context).createOrUpdate(entity);
    }

    public boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}
