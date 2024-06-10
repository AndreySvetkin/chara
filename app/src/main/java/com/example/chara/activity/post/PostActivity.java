package com.example.chara.activity.post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Post;
import com.example.chara.service.PostService;

import java.util.List;

import retrofit2.Retrofit;

public class PostActivity extends AppCompatActivity {

    private List<Post> posts;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private PostService postService = retrofit.create(PostService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }

    private void loadedPosts(List<Post> posts){
        this.posts = posts;
    }

    private void allPosts() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedPosts", List.class);

        loadHelper.loadData(postService.allPosts());
    }
}