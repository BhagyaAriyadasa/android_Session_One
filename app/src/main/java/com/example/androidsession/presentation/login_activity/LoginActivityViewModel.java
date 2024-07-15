package com.example.androidsession.presentation.login_activity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.LoginDS;

import org.json.JSONException;

public class LoginActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> loginResults;

    public LoginActivityViewModel(){
        loginResults = new MutableLiveData<>();
    }

    public LiveData<Boolean> getLoginResult(){
        return loginResults;
    }

    public void login(Context context, String username, String password){
        LoginDS loginDS = new LoginDS(context);

        try {
            boolean isActive = loginDS.getIsActive(username,password);
            loginResults.postValue(isActive);
        }catch (JSONException e){
            e.printStackTrace();
            loginResults.postValue(false);
        }
    }
}
