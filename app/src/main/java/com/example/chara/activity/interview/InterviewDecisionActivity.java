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

public class InterviewDecisionActivity extends AppCompatActivity {

    private CheckBox checkboxAccepted;
    private LinearLayout layoutDetails;
    private EditText editPosition;
    private EditText editSalary;
    private ImageButton buttonSave;

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
            goBack();
        } else {
            Toast.makeText(this, "Decision saved: Rejected", Toast.LENGTH_LONG).show();
            goBack();
        }

    }

    private void goBack() {
        Intent intent = new Intent(this, InterviewListActivity.class);
        startActivity(intent);
    }
}
