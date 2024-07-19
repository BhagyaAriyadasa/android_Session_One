package com.example.androidsession.http.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.androidsession.database.table_entity.ProfileEntity;
import com.example.androidsession.http.ApiController;
import com.example.androidsession.http.ResponseCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetHS {

    int id =2;
    /*
    * 1 : profile
    * 2 : login
    * 3 : city
    * 4 : team
     */
    int type = 1;

    public void execute(ResponseCall<List<ProfileEntity>> callBack){
        Call<List<ProfileEntity>> response = ApiController.getInstance().get(id,type);
        response.enqueue(new Callback<List<ProfileEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProfileEntity>> call, @NonNull Response<List<ProfileEntity>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ProfileEntity>> call, @NonNull Throwable throwable) {
                callBack.onError(throwable.getMessage());
            }
        });
    }

//    public interface ResponseCall{
//        void onSuccess(List<ProfileEntity> data);
//        void onError(String error);
//
//    }
}
