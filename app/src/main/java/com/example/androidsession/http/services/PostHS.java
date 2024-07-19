package com.example.androidsession.http.services;

import androidx.annotation.NonNull;

import com.example.androidsession.Utils;
import com.example.androidsession.database.table_entity.ProfileEntity;
import com.example.androidsession.http.ApiController;
import com.example.androidsession.http.ResponseCall;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostHS {

    int id = 2;
    int type = 1;
    ProfileEntity body;
    public PostHS(ProfileEntity body){
        this.body = body;
    }

    public void execute(ResponseCall<List<ProfileEntity>> callBack){
        Call<List<ProfileEntity>> response = ApiController.getInstance().post(id,type, Utils.getJsonElement(body));
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
}
