package com.example.chara.activity.interview;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Interview;
import com.example.chara.model.Post;
import com.example.chara.model.Resume;
import com.example.chara.service.EmployeeService;
import com.example.chara.service.InterviewService;
import com.example.chara.service.PostService;
import com.example.chara.service.ResumeService;

import java.util.List;

import retrofit2.Retrofit;

public class InterviewDecisionActivity extends AppCompatActivity {

    private CheckBox checkboxAccepted;
    private LinearLayout layoutDetails;
    private EditText editPosition;
    private EditText editSalary;
    private ImageButton buttonSave;
    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private List<Post> posts;

    private PostService postService = retrofit.create(PostService.class);
    private ResumeService resumeService = retrofit.create(ResumeService.class);
    private EmployeeService employeeService = retrofit.create(EmployeeService.class);
    private com.example.chara.service.InterviewService InterviewService = retrofit.create(com.example.chara.service.InterviewService.class);

    private Interview interview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interview_decision);
        // Initialize views
        checkboxAccepted = findViewById(R.id.checkboxAccepted);
        layoutDetails = findViewById(R.id.layoutDetails);
        editPosition = findViewById(R.id.editPosition);
        editSalary = findViewById(R.id.editSalary);
        buttonSave = findViewById(R.id.buttonSave);
        allPosts();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("interview")) {
            interview = (Interview) intent.getSerializableExtra("interview");
        }

        // Set checkbox listener
        checkboxAccepted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layoutDetails.setVisibility(View.VISIBLE);
            } else {
                layoutDetails.setVisibility(View.GONE);
            }
        });

        // Set button save listener
        buttonSave.setOnClickListener(v -> saveDecision());
    }

    private void saveDecision() {
        if (checkboxAccepted.isChecked()) {
            String position = editPosition.getText().toString().trim();
            String salary = editSalary.getText().toString().trim();

            if (TextUtils.isEmpty(position) || TextUtils.isEmpty(salary)) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Decision saved: Accepted", Toast.LENGTH_LONG).show();
            ReplaceSeekerToEmployee(interview.getResume(), salary, position);
            goBack();
        } else {
            Toast.makeText(this, "Decision saved: Rejected", Toast.LENGTH_LONG).show();
            deleteResume(interview.getResume());
            goBack();
        }

    }

    private void ReplaceSeekerToEmployee(Resume r, String salary, String position) {
        Post post = posts.stream()
                .filter(post1 -> post1.getName().equals(position))
                .findFirst().orElse(null);

        if (post == null) post = posts.get(0);
        String[] fio = r.getFio().split(" ");
        Employee emp = new Employee(fio[0], fio[1], fio[2], Double.parseDouble(salary), r.getPhone(), "", post);
        addEmployee(emp);
        deleteInterview(interview);
        deleteResume(r);
    }

    private void goBack() {
        Intent intent = new Intent(this, InterviewListActivity.class);
        startActivity(intent);
    }

    public void uploadedEmployee(Employee employee){

    }

    public void deletedResume(){

    }

    public void loadedPosts(List<Post> posts){

        this.posts = posts;
    }


    private void deleteResume(Resume resume) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedResume");

        loadHelper.loadData(resumeService.deleteResume(resume));
    }

    public void addEmployee(Employee employee) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedEmployee", Employee.class);

        loadHelper.loadData(employeeService.addEmployee(employee));
    }

    private void allPosts() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedPosts", List.class);

        loadHelper.loadData(postService.allPosts());
    }

    private void deleteInterview(Interview interview) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedInterview", Interview.class);

        loadHelper.loadData(InterviewService.deleteInterview(interview));
    }

    private void deletedInterview(Interview interview){

    }
}
