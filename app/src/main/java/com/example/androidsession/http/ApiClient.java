package com.example.androidsession.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit clientInstance;
    private  static final String baseURL = "https://check-apis.onrender.com/copy";

    public static Retrofit getClient(){

        if(clientInstance==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(System.err::println);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            clientInstance = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();

        }
        return clientInstance;
    }
}
