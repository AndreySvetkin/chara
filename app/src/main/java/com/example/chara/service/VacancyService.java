package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Post;
import com.example.chara.model.Vacancy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VacancyService {
    final String VIEW = "vacancy-view";

    default Call<List<Vacancy>> allVacancies() {
        return allVacancies(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Vacancy")
    Call<List<Vacancy>> allVacancies(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Vacancy> fetchVacancy(Vacancy vacancy) {
        return fetchVacancy(" Bearer " + UserServiceHelper.ACCESS_TOKEN, vacancy.getId(), VIEW);
    }
    @GET("entities/chara_Vacancy/{vacancyId}")
    Call<Vacancy> fetchVacancy(@Header("Authorization") String auth, @Path("vacancyId") String vacancyId, @Query("view") String view);
}
