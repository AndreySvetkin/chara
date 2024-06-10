package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Interview;

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

public interface InterviewService {

    final String VIEW = "interview-view";

    default Call<List<Interview>> allInterviews() {
        return allInterviews(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Interview")
    Call<List<Interview>> allInterviews(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Interview> fetchInterview(Interview interview) {
        return fetchInterview(" Bearer " + UserServiceHelper.ACCESS_TOKEN, interview.getId(), VIEW);
    }
    @GET("entities/chara_Interview/{interviewId}")
    Call<Interview> fetchInterview(@Header("Authorization") String auth, @Path("departId") String interviewId, @Query("view") String view);

    default Call<Interview> addInterview(Interview interview) {
        return addInterview(" Bearer " + UserServiceHelper.ACCESS_TOKEN, interview, VIEW);
    }
    @POST("entities/chara_Interview/")
    Call<Interview> addInterview(@Header("Authorization") String auth, @Body Interview interview, @Query("responseView") String responseView);

    default Call<Interview> saveInterview(Interview interview) {
        return saveInterview(" Bearer " + UserServiceHelper.ACCESS_TOKEN, interview.getId(), interview, VIEW);
    }
    @PUT("entities/chara_Interview/{interviewId}")
    Call<Interview> saveInterview(@Header("Authorization") String auth, @Path("interviewId") String interviewId, @Body Interview interview, @Query("responseView") String responseView);

    default Call<Interview> deleteInterview(Interview interview) {
        return deleteInterview(" Bearer " + UserServiceHelper.ACCESS_TOKEN, interview.getId());
    }
    @DELETE("entities/chara_Interview/{interviewId}")
    Call<Interview> deleteInterview(@Header("Authorization") String auth, @Path("interviewId") String interviewId);
}
