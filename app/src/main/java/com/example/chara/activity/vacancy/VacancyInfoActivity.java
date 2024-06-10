package com.example.chara.activity.vacancy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;
import com.example.chara.model.Vacancy;

public class VacancyInfoActivity extends AppCompatActivity {

    private TextView nameTextView, departTextView, requirementsTextView, circsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_vacancy);

        nameTextView = findViewById(R.id.textVacancyName);
        departTextView = findViewById(R.id.textVacancyDepart);
        requirementsTextView = findViewById(R.id.textVacancyRequirements);
        circsTextView = findViewById(R.id.textVacancyCircs);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("vacancy")) {
            Vacancy vacancy = (Vacancy) intent.getSerializableExtra("vacancy");
            if (vacancy != null) {
                displayVacancyDetails(vacancy);
            } else {
                Toast.makeText(this, "Нет данных о вакансии", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Нет данных о вакансии", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayVacancyDetails(Vacancy vacancy) {
        nameTextView.setText(vacancy.getName() != null ? "Название: " + vacancy.getName() : "Название: Нет данных");
        departTextView.setText(vacancy.getDepart() != null ? "Отдел: " + vacancy.getDepart().getName() : "Отдел: Нет данных");
        requirementsTextView.setText(vacancy.getRequirements() != null ? "Требования: " + vacancy.getRequirements() : "Требования: Нет данных");
        circsTextView.setText(vacancy.getCircs() != null ? "Условия: " + vacancy.getCircs() : "Условия: Нет данных");
    }
}
