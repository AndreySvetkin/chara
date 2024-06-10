package com.example.chara.activity.vacancy;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.R;
import com.example.chara.activity.employee.EmployeeInfoActivity;
import com.example.chara.activity.seeker.SeekerListActivity;
import com.example.chara.adapter.SeekerAdapter;
import com.example.chara.adapter.VacancyAdapter;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Vacancy;
import com.example.chara.service.VacancyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;


public class VacancyListActivity extends AppCompatActivity {
    List<Vacancy> vacancyList;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();
    private RecyclerView recyclerView;

    private VacancyService vacancyService = retrofit.create(VacancyService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_vacancies);
        recyclerView = findViewById(R.id.vacancy_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        allVacancies();
    }

    public void loadedVacancies(List<Vacancy> vacancies){
        this.vacancyList = vacancies;
        VacancyAdapter adapter = new VacancyAdapter(this, vacancyList, new VacancyAdapter.OnVacancyClickListener() {
            @Override
            public void onVacancyClick(int position) {
                Vacancy vacancy = vacancyList.get(position);
                Intent intent = new Intent(VacancyListActivity.this, VacancyInfoActivity.class);
                intent.putExtra("vacancy", vacancy);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void allVacancies() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedVacancies", List.class);

        loadHelper.loadData(vacancyService.allVacancies());
    }
}