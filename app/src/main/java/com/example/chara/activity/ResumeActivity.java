package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Post;
import com.example.chara.model.Resume;
import com.example.chara.service.ResumeService;

import java.util.List;

import retrofit2.Retrofit;

public class ResumeActivity extends AppCompatActivity {

    private List<Resume> resumes;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private ResumeService resumeService = retrofit.create(ResumeService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
    }

    private void loadedResumes(List<Resume> resumes){
        this.resumes = resumes;
    }

    public void deletedResume(){

    }

    private void allResumes() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedResumes", List.class);

        loadHelper.loadData(resumeService.allResumes());
    }

    private void deleteResume(Resume resume) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedResume");

        loadHelper.loadData(resumeService.deleteResume(resume));
    }
}