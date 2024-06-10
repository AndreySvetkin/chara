package com.example.chara.activity.interview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;

public class AddInterviewActivity extends AppCompatActivity {

    private EditText editCandidateName, editPosition, editInterviewDate, editInterviewTime, editInterviewerName;
    private Button btnSaveInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview);

        editCandidateName = findViewById(R.id.edit_candidate_name);
        editPosition = findViewById(R.id.edit_position);
        editInterviewDate = findViewById(R.id.edit_interview_date);
        editInterviewTime = findViewById(R.id.edit_interview_time);
        editInterviewerName = findViewById(R.id.edit_interviewer_name);
        btnSaveInterview = findViewById(R.id.btn_save_interview);

        btnSaveInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidateName = editCandidateName.getText().toString();
                String position = editPosition.getText().toString();
                String interviewDate = editInterviewDate.getText().toString();
                String interviewTime = editInterviewTime.getText().toString();
                String interviewerName = editInterviewerName.getText().toString();

                if (!candidateName.isEmpty() && !position.isEmpty() && !interviewDate.isEmpty() && !interviewTime.isEmpty() && !interviewerName.isEmpty()) {
                    // Создать объект Interview и сохранить его в базе данных или передать обратно в InterviewListActivity
                    finish();
                } else {
                    Toast.makeText(AddInterviewActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
