package com.example.chara.activity.seeker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.R;
import com.example.chara.activity.employee.EmployeeInfoActivity;
import com.example.chara.activity.interview.InterviewListActivity;
import com.example.chara.adapter.InterviewAdapter;
import com.example.chara.adapter.SeekerAdapter;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Resume;
import com.example.chara.service.ResumeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class SeekerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeekerAdapter seekerAdapter;
    private List<Resume> resumeList;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();
    private ResumeService resumeService = retrofit.create(ResumeService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_seekers);
        recyclerView = findViewById(R.id.recycleViewApplicants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allResumes();
    }

    public void loadedResumes(List<Resume> resumes){
        this.resumeList = resumes;
        seekerAdapter = new SeekerAdapter(this, resumeList, new SeekerAdapter.OnResumeClickListener() {
            @Override
            public void onResumeClick(int position) {
                Resume resume = resumeList.get(position);
                Intent intent = new Intent(SeekerListActivity.this, SeekerProfile.class);
                intent.putExtra("resume", resume);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(seekerAdapter);
    }

    private void allResumes() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedResumes", List.class);
        loadHelper.loadData(resumeService.allResumes());
    }


}
