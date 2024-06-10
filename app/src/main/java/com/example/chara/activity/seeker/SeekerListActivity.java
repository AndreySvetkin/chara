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

    public static List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();

        resumes.add(new Resume("1", "Иван Иванов", new Date(), "+79012345678", "ivanov@example.com", "Высшее образование", "Коммуникабельность, ответственность", "Java, Python"));
        resumes.add(new Resume("2", "Петр Петров", new Date(), "+79087654321", "petrov@example.com", "Высшее образование", "Умение работать в команде", "C++, JavaScript"));
        resumes.add(new Resume("3", "Анна Сидорова", new Date(), "+79161234567", "sidorova@example.com", "Высшее образование", "Ответственность, внимательность", "Python, Flutter"));
        resumes.add(new Resume("4", "Дмитрий Кузнецов", new Date(), "+79165432109", "kuznetsov@example.com", "Высшее образование", "Коммуникабельность, нацеленность на результат", "Java, C#"));
        resumes.add(new Resume("5", "Екатерина Смирнова", new Date(), "+79162345678", "smirnova@example.com", "Высшее образование", "Умение работать в команде, креативность", "JavaScript, Flutter"));

        return resumes;
    }

    private void loadedResumes(List<Resume> resumes){
        this.resumeList = resumes;
    }

    private void allResumes() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedResumes", List.class);

        loadHelper.loadData(resumeService.allResumes());
    }


}
