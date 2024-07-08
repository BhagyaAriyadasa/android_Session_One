package com.example.androidsession.presentation.main_activity;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.table_entity.CityEntity;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public MutableLiveData<String> getText() {
        if (text == null){
            text = new MutableLiveData<>();
        }
        return text;
    }

    public void clickMeMethod(Context context){
        if (text == null){
            text = new MutableLiveData<>();
        }
        text.postValue("test 2");
        saveCity(context);
    }

    private void saveCity(Context context){
        CityEntity entity = new CityEntity();
        entity.setIsActive(true);
        entity.setName("a");
        entity.setUID(1);
        new CityDS(context).createOrUpdate(entity);
    }
}
