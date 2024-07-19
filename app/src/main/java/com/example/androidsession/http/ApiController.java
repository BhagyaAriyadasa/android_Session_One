package com.example.androidsession.http;

import com.example.androidsession.database.table_entity.ProfileEntity;
import com.google.gson.JsonElement;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ApiController implements Apis {

    private final Apis apis;
    private static ApiController apiController;

    public ApiController(){
        Retrofit client = ApiClient.getClient();
        apis = client.create(Apis.class);
    }

    public static synchronized ApiController getInstance(){
        if(apiController==null){
            apiController = new ApiController();
        }
        return apiController;
    }

    @Override
    public Call<List<ProfileEntity>> get(int id, int type) {
        return apis.get(id,type);
    }

    @Override
    public Call<List<ProfileEntity>> post(int id, int type, JsonElement body) {
        return apis.post(id,type,body);
    }

    @Override
    public Call<ResponseBody> delete(int id, int type, String uid) {
        return apis.delete(id,type,uid);
    }
}
