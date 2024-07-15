package com.example.androidsession.presentation.home_page_activity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.table_entity.LoginEntity;

import org.json.JSONException;

import java.util.List;

public class HomePageActivityViewModel extends ViewModel {
    private MutableLiveData<List<LoginEntity>> activeUserData;
    private MutableLiveData<List<LoginEntity>> inactiveUserData;

    HomePageActivityViewModel(){
        activeUserData = new MutableLiveData<>();
        inactiveUserData = new MutableLiveData<>();
    }

    public LiveData<List<LoginEntity>> getActiveUsers() {
        return activeUserData;
    }

    public LiveData<List<LoginEntity>> getInactiveUsers() {
        return inactiveUserData;
    }

    public void fetchActiveUsers(Context context) {
        LoginDS loginDS = new LoginDS(context);
        try {
            List<LoginEntity> activeUsers = loginDS.getActiveUsers();
            activeUserData.postValue(activeUsers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchInactiveUsers(Context context) {
        LoginDS loginDS = new LoginDS(context);
        try {
            List<LoginEntity> inactiveUsers = loginDS.getInactiveUsers();
            inactiveUserData.postValue(inactiveUsers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deactivateUser(Context context, LoginEntity user) {
        LoginDS loginDS = new LoginDS(context);
        loginDS.updateUser(user);
    }

    public void activateUser(Context context, LoginEntity user){
        LoginDS loginDS = new LoginDS(context);
        loginDS.updateUser(user);
    }
}
