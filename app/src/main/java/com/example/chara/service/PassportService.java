package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Passport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PassportService {

    final String VIEW = "passport-view";

    default Call<List<Passport>> allPassports() {
        return allPassports(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Passport")
    Call<List<Passport>> allPassports(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Passport> fetchPassport(Passport passport) {
        return fetchPassport(" Bearer " + UserServiceHelper.ACCESS_TOKEN, passport.getId(), VIEW);
    }
    @GET("entities/chara_Passport/{passportId}")
    Call<Passport> fetchPassport(@Header("Authorization") String auth, @Path("passportId") String passportId, @Query("view") String view);

    default Call<Passport> addPassport(Passport passport) {
        return addPassport(" Bearer " + UserServiceHelper.ACCESS_TOKEN, passport, VIEW);
    }
    @POST("entities/chara_Passport/")
    Call<Passport> addPassport(@Header("Authorization") String auth, @Body Passport passport, @Query("responseView") String responseView);

    default Call<Passport> savePassport(Passport passport) {
        return savePassport(" Bearer " + UserServiceHelper.ACCESS_TOKEN, passport.getId(), passport, VIEW);
    }
    @PUT("entities/chara_Passport/{passportId}")
    Call<Passport> savePassport(@Header("Authorization") String auth, @Path("passportId") String passportId, @Body Passport passport, @Query("responseView") String responseView);

    default Call<Passport> deletePassport(Passport passport) {
        return deletePassport(" Bearer " + UserServiceHelper.ACCESS_TOKEN, passport.getId());
    }
    @DELETE("entities/chara_Passport/{passportId}")
    Call<Passport> deletePassport(@Header("Authorization") String auth, @Path("passportId") String passportId);
}
