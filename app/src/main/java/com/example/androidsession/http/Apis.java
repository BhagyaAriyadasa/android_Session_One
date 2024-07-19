package com.example.androidsession.http;

import com.example.androidsession.database.table_entity.ProfileEntity;
import com.google.gson.JsonElement;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apis {
    @Headers({"Content-Type: application/json"})
    @GET("/get")
    Call<List<ProfileEntity>> get(@Query("id") int id, @Query("type") int type);

    @Headers({"Content-Type: application/json"})
    @POST("/post")
    Call<List<ProfileEntity>> post(@Query("id") int id, @Query("type") int type, @Body JsonElement body);

    @Headers({"Content-Type: application/json"})
    @DELETE("/delete")
    Call<ResponseBody> delete(@Query("id") int id, @Query("type") int type, @Query("uid") String uid);
}
