package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;

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

public interface DepartService {

    final String VIEW = "depart-view";

    default Call<List<Depart>> allDeparts() {
        return allDeparts(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Depart")
    Call<List<Depart>> allDeparts(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Depart> fetchDepart(Depart depart) {
        return fetchDepart(" Bearer " + UserServiceHelper.ACCESS_TOKEN, depart.getId(), VIEW);
    }
    @GET("entities/chara_Depart/{departId}")
    Call<Depart> fetchDepart(@Header("Authorization") String auth, @Path("departId") String departId, @Query("view") String view);

    default Call<Depart> addDepart(Depart depart) {
        return addDepart(" Bearer " + UserServiceHelper.ACCESS_TOKEN, depart, VIEW);
    }
    @POST("entities/chara_Depart/")
    Call<Depart> addDepart(@Header("Authorization") String auth, @Body Depart depart, @Query("responseView") String responseView);

    default Call<Depart> saveDepart(Depart depart) {
        return saveDepart(" Bearer " + UserServiceHelper.ACCESS_TOKEN, depart.getId(), depart, VIEW);
    }
    @PUT("entities/chara_Depart/{departId}")
    Call<Depart> saveDepart(@Header("Authorization") String auth, @Path("departId") String departId, @Body Depart depart,  @Query("responseView") String responseView);

    default Call<Depart> deleteDepart(Depart depart) {
        return deleteDepart(" Bearer " + UserServiceHelper.ACCESS_TOKEN, depart.getId());
    }
    @DELETE("entities/chara_Depart/{departId}")
    Call<Depart> deleteDepart(@Header("Authorization") String auth, @Path("departId") String departId);
}
