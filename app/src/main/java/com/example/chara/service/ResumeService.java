package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Post;
import com.example.chara.model.Resume;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ResumeService {

    final String VIEW = "post-view";

    default Call<List<Resume>> allResumes() {
        return allResumes(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Post")
    Call<List<Resume>> allResumes(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Post> fetchResume(Resume resume) {
        return fetchResume(" Bearer " + UserServiceHelper.ACCESS_TOKEN, resume.getId(), VIEW);
    }
    @GET("entities/chara_Resume/{resumeId}")
    Call<Post> fetchResume(@Header("Authorization") String auth, @Path("resumeId") String resumeId, @Query("view") String view);
}
