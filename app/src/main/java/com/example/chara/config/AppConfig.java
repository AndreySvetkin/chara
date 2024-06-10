package com.example.chara.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppConfig {

    public static String ACCESS_TOKEN;

    public static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .create();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .baseUrl("http://192.168.1.5:8080/app/rest/v2/")
                .build();
    }
}
