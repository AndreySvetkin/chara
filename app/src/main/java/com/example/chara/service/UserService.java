package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Profile;
import com.example.chara.model.Resume;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    final String VIEW = "user-view";

    default Call<Map<String, String>> getToken(String login, String password){
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        return getToken(
                "application/x-www-form-urlencoded",
                UserServiceHelper.base64RestParams(),
                RequestBody.create(mediaType, UserServiceHelper.userParams(login, password)));
    }

    default Call<Map<String, String>> getToken(){
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        return getToken(
                "application/x-www-form-urlencoded",
                UserServiceHelper.base64RestParams(),
                RequestBody.create(mediaType, UserServiceHelper.userParams(null, null)));
    }

    @POST("oauth/token")
    Call<Map<String, String>> getToken(
            @Header("Content-Type") String contentType,
            @Header("Authorization") String base64RestParams,
            @Body RequestBody userParams);


    default Call<Profile> createUser(Profile profile) {
        return createUser(" Bearer " + UserServiceHelper.ACCESS_TOKEN, profile, VIEW);
    }
    @POST("entities/sec$User")
    Call<Profile> createUser(@Header("Authorization") String auth, @Body Profile profile, @Query("responseView") String responseView);
}
