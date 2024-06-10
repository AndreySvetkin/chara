package com.example.chara.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chara.R;
import com.example.chara.activity.employee.EmployeeListActivity;
import com.example.chara.activity.interview.InterviewDecisionActivity;
import com.example.chara.activity.interview.InterviewListActivity;
import com.example.chara.activity.task.TaskListActivity;
import com.example.chara.activity.user.Authorization;
import com.example.chara.activity.seeker.SeekerListActivity;
import com.example.chara.activity.vacancy.VacancyListActivity;
import com.example.chara.activity.user.ProfileActivity;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.service.DepartService;
import com.example.chara.service.UserService;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private List<Depart> departs;


    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private DepartService departService = retrofit.create(DepartService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        if (UserServiceHelper.ACCESS_TOKEN == null){
            initSession();
        }

//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104/mobile_app", "android", "1234");
//            Statement st = conn.createStatement();
//            conn.close();
//        } catch(Exception e){
//            e.printStackTrace();
//        }

//            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//            sharedPreferences.edit().clear().apply();
        // Проверяем, было ли уже выполнено вход в приложение
        boolean isUserLoggedIn = checkIfUserLoggedIn();
        isUserLoggedIn = true;
        if (!isUserLoggedIn) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
            finish();
            return;
        }
        CardView openVacanciesButton = findViewById(R.id.cardVacancies);
        CardView openInterviewButton = findViewById(R.id.cardInterviews);
        CardView openSeekersButton = findViewById(R.id.cardSeekers);
        CardView openTasksButton = findViewById(R.id.cardTasks);
        CardView openEmployeesButton = findViewById(R.id.cardEmployees);
        openVacanciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VacancyListActivity.class);
                startActivity(intent);
            }
        });

        openInterviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterviewListActivity.class);
                startActivity(intent);
            }
        });

        openSeekersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeekerListActivity.class);
                startActivity(intent);
            }
        });

        openTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

        openEmployeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
                startActivity(intent);
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Личный кабинет":
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "Выход":
                        logoutUser();
                }

                return false;
            }
        });

    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
        finish();
    }

    private boolean checkIfUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        if (savedUsername != "") return true;
        else return false;
    }

    private void loadedDeparts(List<Depart> departs){
        this.departs = departs;
    }

    private void uploadedDepart(Depart depart){

    }

    private void deletedDepart(Depart depart){

    }

    public void loadSession(Map<String, String> map) {
        UserServiceHelper.ACCESS_TOKEN = map.get("access_token");
        System.out.println(UserServiceHelper.ACCESS_TOKEN);
        recreate();
    }

    private void initSession() {
        UserService userService = retrofit.create(UserService.class);
        LoadHelper loadHelper = new LoadHelper(this, "loadSession", Map.class);

        loadHelper.loadData(userService.getToken());
    }

    private void allDeparts() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedDeparts", List.class);

        loadHelper.loadData(departService.allDeparts());
    }

    private void addDepart(Depart depart) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedDepart", Depart.class);

        loadHelper.loadData(departService.addDepart(depart));
    }

    private void deleteDepart(Depart depart) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedDepart", Depart.class);

        loadHelper.loadData(departService.deleteDepart(depart));
    }


}