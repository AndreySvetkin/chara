package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Post;

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

public interface PostService {

    final String VIEW = "post-view";

    default Call<List<Post>> allPosts() {
        return allPosts(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Post")
    Call<List<Post>> allPosts(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Post> fetchPost(Post post) {
        return fetchPost(" Bearer " + UserServiceHelper.ACCESS_TOKEN, post.getId(), VIEW);
    }
    @GET("entities/chara_Post/{postId}")
    Call<Post> fetchPost(@Header("Authorization") String auth, @Path("postId") String postId, @Query("view") String view);

}
