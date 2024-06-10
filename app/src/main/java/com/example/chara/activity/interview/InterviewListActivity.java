package com.example.chara.activity.interview;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.activity.employee.EmployeeInfoActivity;
import com.example.chara.activity.employee.EmployeeListActivity;
import com.example.chara.adapter.EmployeeAdapter;
import com.example.chara.adapter.InterviewAdapter;
import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Employee;
import com.example.chara.model.Interview;
import com.example.chara.model.Post;
import com.example.chara.model.Resume;
import com.example.chara.model.Vacancy;
import com.example.chara.service.InterviewService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class InterviewListActivity extends AppCompatActivity {

    private List<Interview> interviewList;
    private InterviewAdapter adapter;
    private Retrofit retrofit = AppConfig.getRetrofitInstance();
    private com.example.chara.service.InterviewService InterviewService = retrofit.create(InterviewService.class);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_interview_activity);

        recyclerView = findViewById(R.id.interview_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allInterviews();
    }

    public void loadedInterviews(List<Interview> interviews){
        this.interviewList = interviews;
        adapter = new InterviewAdapter(this, interviewList, new InterviewAdapter.OnInterviewClickListener() {
            @Override
            public void onInterviewClick(int position) {
                Interview interview = interviewList.get(position);
                Intent intent = new Intent(InterviewListActivity.this, InterviewDecisionActivity.class);
                intent.putExtra("interview", interview);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
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
