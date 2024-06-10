package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Interview;
import com.example.chara.model.Post;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportService {

    default Call<List<Interview>> passedInterviews() {
        return passedInterviews(" Bearer " + UserServiceHelper.ACCESS_TOKEN);
    }
    @GET("queries/chara_Interview/passedInterviews")
    Call<List<Interview>> passedInterviews(@Header("Authorization") String auth);

    default Call<List<Interview>> interviewsBetweenDates(Date startDate, Date endDate) {
        return interviewsBetweenDates(" Bearer " + UserServiceHelper.ACCESS_TOKEN, startDate, endDate);
    }
    @GET("queries/chara_Interview/passedInterviews")
    Call<List<Interview>> interviewsBetweenDates(@Header("Authorization") String auth, @Query("startDate") Date startDate, @Query("endDate") Date endDate);

    default Call<List<Interview>> passedInterviewsBetweenDates(Date startDate, Date endDate) {
        return interviewsBetweenDates(" Bearer " + UserServiceHelper.ACCESS_TOKEN, startDate, endDate);
    }
    @GET("queries/chara_Interview/passedInterviewsBetweenDates")
    Call<List<Interview>> passedInterviewsBetweenDates(@Header("Authorization") String auth, @Query("startDate") Date startDate, @Query("endDate") Date endDate);

    default Call<Double> avgSalary() {
        return avgSalary(" Bearer " + UserServiceHelper.ACCESS_TOKEN);
    }
    @GET("queries/chara_Employee/avgSalary")
    Call<Double> avgSalary(@Header("Authorization") String auth);

    default Call<Double> avgSalaryInDepart(Depart depart) {
        return avgSalaryInDepart(" Bearer " + UserServiceHelper.ACCESS_TOKEN, depart.getId());
    }
    @GET("queries/chara_Depart/avgSalaryInDepart")
    Call<Double> avgSalaryInDepart(@Header("Authorization") String auth, @Query("departId") String departId);
}
