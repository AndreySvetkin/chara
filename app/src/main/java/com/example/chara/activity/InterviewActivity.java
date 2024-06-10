package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Interview;
import com.example.chara.service.InterviewService;

import java.util.List;

import retrofit2.Retrofit;

public class InterviewActivity extends AppCompatActivity {

    private List<Interview> interviews;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private InterviewService InterviewService = retrofit.create(InterviewService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
    }

    private void loadedInterviews(List<Interview> interviews){
        this.interviews = interviews;
    }

    private void uploadedInterview(Interview interview){

    }

    private void deletedInterview(Interview interview){

    }

    private void allInterviews() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedInterviews", List.class);

        loadHelper.loadData(InterviewService.allInterviews());
    }

    private void addInterview(Interview interview) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedInterview", Interview.class);

        loadHelper.loadData(InterviewService.addInterview(interview));
    }

    private void deleteInterview(Interview interview) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedInterview", Interview.class);

        loadHelper.loadData(InterviewService.deleteInterview(interview));
    }
}