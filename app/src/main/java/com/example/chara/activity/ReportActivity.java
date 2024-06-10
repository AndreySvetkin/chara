package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Interview;
import com.example.chara.model.Resume;
import com.example.chara.service.ReportService;
import com.example.chara.service.ResumeService;

import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class ReportActivity extends AppCompatActivity {

    private List<Interview> interviews;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private ReportService reportService = retrofit.create(ReportService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    private void loadedInterviews(List<Interview> interviews){
        this.interviews = interviews;
    }

    private void loadedAvgSalary(Double avgSalary){
    }

    private void passedInterviews() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedInterviews", List.class);

        loadHelper.loadData(reportService.passedInterviews());
    }
    private void interviewsBetweenDates(Date startDate, Date endDate) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedInterviews", List.class);

        loadHelper.loadData(reportService.interviewsBetweenDates(startDate, endDate));
    }
    private void passedInterviewsBetweenDates(Date startDate, Date endDate) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedInterviews", List.class);

        loadHelper.loadData(reportService.passedInterviewsBetweenDates(startDate, endDate));
    }
    private void avgSalary() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedAvgSalary", Double.class);

        loadHelper.loadData(reportService.avgSalary());
    }
    private void avgSalaryInDepart(Depart depart) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedAvgSalary", Double.class);

        loadHelper.loadData(reportService.avgSalaryInDepart(depart));
    }

}