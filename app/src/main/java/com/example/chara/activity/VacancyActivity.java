package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Post;
import com.example.chara.model.Vacancy;
import com.example.chara.service.PostService;
import com.example.chara.service.VacancyService;

import java.util.List;

import retrofit2.Retrofit;

public class VacancyActivity extends AppCompatActivity {

    private List<Vacancy> vacancies;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private VacancyService vacancyService = retrofit.create(VacancyService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);
    }

    private void loadedVacancies(List<Vacancy> Vacancies){
        this.vacancies = vacancies;
    }

    private void allVacancies() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedVacancies", List.class);

        loadHelper.loadData(vacancyService.allVacancies());
    }
}