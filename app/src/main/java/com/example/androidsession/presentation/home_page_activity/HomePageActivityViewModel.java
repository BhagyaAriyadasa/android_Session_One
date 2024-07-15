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

    HomePageActivityViewModel(){
        activeUserData = new MutableLiveData<>();
    }

    public LiveData<List<LoginEntity>> getActiveUsers() {
        return activeUserData;
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

    public void deactivateUser(Context context, LoginEntity user) {
//        user.setIsActive(false);
        LoginDS loginDS = new LoginDS(context);
        loginDS.updateUser(user);
    }
}
