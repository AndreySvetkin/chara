package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Interview;
import com.example.chara.service.EmployeeService;
import com.example.chara.service.InterviewService;

import retrofit2.Retrofit;

public class InterviewEditActivity extends AppCompatActivity {

    private Interview interview;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private InterviewService interviewService = retrofit.create(InterviewService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_edit);
    }

    private void loadedInterview(Interview interview){
        this.interview = interview;
    }

    private void updatedInterview(Interview interview){

    }

    private void fetchInterview(Interview Interview) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedInterview", Interview.class);

        loadHelper.loadData(interviewService.fetchInterview(interview));
    }

    private void saveInterview() {
        LoadHelper loadHelper = new LoadHelper(this, "updatedInterview", Interview.class);

        loadHelper.loadData(interviewService.saveInterview(interview));
    }
}