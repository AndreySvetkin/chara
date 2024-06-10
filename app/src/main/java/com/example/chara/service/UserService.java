package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {


    default Call<Map<String, String>> getToken(){
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        return getToken(
                "application/x-www-form-urlencoded",
                UserServiceHelper.base64RestParams(),
                RequestBody.create(mediaType, UserServiceHelper.userParams()));
    }

    @POST("oauth/token")
    Call<Map<String, String>> getToken(
            @Header("Content-Type") String contentType,
            @Header("Authorization") String base64RestParams,
            @Body RequestBody userParams);
}
