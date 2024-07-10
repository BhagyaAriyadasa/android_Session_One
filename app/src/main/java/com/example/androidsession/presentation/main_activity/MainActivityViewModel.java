package com.example.androidsession.presentation.main_activity;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.data_service.ProfileDS;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.LoginEntity;
import com.example.androidsession.database.table_entity.ProfileEntity;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> text;

//    public MutableLiveData<String> getText() {
//        if (text == null){
//            text = new MutableLiveData<>();
//        }
//        return text;
//    }

    public void register(Context context, String name, int age, int city, int team, double salary, int loginUid){
        saveProfile(context, name, age, city, team, salary, loginUid);
    }

    public void login(Context context, String userName, String password, int isActive){
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

    private void saveLogin(Context context, String userName, String password, int isActive){
        LoginEntity entity = new LoginEntity();
        if (isActive == 0){
            entity.setIsActive(false);
        } else{
            entity.setIsActive(true);
        }

        entity.setUserName(userName);
        entity.setPassword(password);

        new LoginDS(context).createOrUpdate(entity);
    }
}
