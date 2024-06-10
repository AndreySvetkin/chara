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
    List<Vacancy> vacancyList = new ArrayList<>(Arrays.asList(
            new Vacancy("Software Engineer", "Разработка и поддержка программного обеспечения"),
            new Vacancy("Data Analyst", "Анализ и интерпретация данных для решения бизнес-задач"),
            new Vacancy("Network Administrator", "Управление и поддержка сетевой инфраструктуры"),
            new Vacancy("Web Developer", "Дизайн и разработка веб-сайтов и веб-приложений"),
            new Vacancy("Cybersecurity Specialist", "Защита компьютерных систем и сетей от кибератак"),
            new Vacancy("Database Administrator", "Управление и поддержка баз данных"),
            new Vacancy("IT Project Manager", "Планирование и надзор за IT-проектами от начала до завершения"),
            new Vacancy("System Administrator", "Поддержка и устранение неполадок компьютерных систем и серверов"),
            new Vacancy("Quality Assurance Engineer", "Разработка и выполнение процессов тестирования для обеспечения качества программного обеспечения"),
            new Vacancy("UI/UX Designer", "Дизайн пользовательских интерфейсов и пользовательского опыта для программных приложений"),
            new Vacancy("Business Analyst", "Определение бизнес-потребностей и рекомендация решений с использованием технологий"),
            new Vacancy("DevOps Engineer", "Автоматизация и оптимизация процессов разработки программного обеспечения"),
            new Vacancy("Technical Support Specialist", "Предоставление технической помощи и поддержки конечным пользователям"),
            new Vacancy("IT Compliance Analyst", "Обеспечение соответствия IT-систем и процессов нормативным требованиям и стандартам"),
            new Vacancy("Cloud Architect", "Проектирование и реализация решений для облачной инфраструктуры"),
            new Vacancy("Mobile App Developer", "Создание и поддержка мобильных приложений для платформ iOS и Android"),
            new Vacancy("IT Trainer", "Предоставление обучения и образования по IT-инструментам и технологиям")
    ));

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private VacancyService vacancyService = retrofit.create(VacancyService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_vacancies);
        RecyclerView recyclerView = findViewById(R.id.vacancy_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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

    private void loadedVacancies(List<Vacancy> vacancies){
        this.vacancyList = vacancies;
    }

    private void allVacancies() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedVacancies", List.class);

        loadHelper.loadData(vacancyService.allVacancies());
    }
}