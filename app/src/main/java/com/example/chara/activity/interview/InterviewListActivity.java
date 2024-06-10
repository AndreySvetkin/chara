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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_interview_activity);

        interviewList = getInterviews();
        RecyclerView recyclerView = findViewById(R.id.interview_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public static List<Interview> getInterviews() {
        List<Interview> interviews = new ArrayList<>();
        List<Resume> r = getResumes();
        interviews.add(new Interview("1", new Date(), r.get(0), new Vacancy( "Разработчик Java", "Описание вакансии Java"), new Employee( "Иван Иванович", new Post("Менеджер по персоналу", new Depart()))));
        interviews.add(new Interview("2", new Date(), r.get(1), new Vacancy("Разработчик C++", "Описание вакансии C++"), new Employee("Петр Петрович", new Post("Технический директор", new Depart()))));
        interviews.add(new Interview("3", new Date(), r.get(2), new Vacancy("Разработчик Flutter", "Описание вакансии Flutter"), new Employee("Анна Андреевна", new Post("Ведущий разработчик", new Depart()))));
        interviews.add(new Interview("4", new Date(), r.get(3), new Vacancy( "Разработчик C#", "Описание вакансии C#"), new Employee("Дмитрий Дмитриевич", new Post("Менеджер по персоналу", new Depart()))));
        interviews.add(new Interview("5", new Date(), r.get(4), new Vacancy("Разработчик JavaScript", "Описание вакансии JavaScript"), new Employee("Екатерина Евгеньевна", new Post("Технический директор", new Depart()))));

        return interviews;
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

    private void loadedInterviews(List<Interview> interviews){
        this.interviewList = interviews;
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
