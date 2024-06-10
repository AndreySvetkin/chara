package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Depart;
import com.example.chara.service.DepartService;
import com.example.chara.service.UserService;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private List<Depart> departs;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private DepartService departService = retrofit.create(DepartService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (UserServiceHelper.ACCESS_TOKEN == null) {
            initSession();
        }
    }

    public void loadSession(Map<String, String> map) {
        UserServiceHelper.ACCESS_TOKEN = map.get("access_token");
        System.out.println(UserServiceHelper.ACCESS_TOKEN);
    }

    private void loadedDeparts(List<Depart> departs){
        this.departs = departs;
    }

    private void uploadedDepart(Depart depart){

    }

    private void deletedDepart(Depart depart){

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