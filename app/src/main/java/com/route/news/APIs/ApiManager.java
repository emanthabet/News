package com.route.news.APIs;

import android.util.Log;

import java.security.Provider;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static Retrofit retrofitinstance;

    private static Retrofit getInstance(){
        if(retrofitinstance==null){
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("api",message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client=new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor).build();
            retrofitinstance = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitinstance;
    }

    public static Services getApis(){
        Services services=getInstance().create(Services.class);
        return  services;
    }
}