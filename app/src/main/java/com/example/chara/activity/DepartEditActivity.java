package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Employee;
import com.example.chara.service.DepartService;
import com.example.chara.service.EmployeeService;

import retrofit2.Retrofit;

public class DepartEditActivity extends AppCompatActivity {

    private Depart depart;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private DepartService departService = retrofit.create(DepartService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_edit);
    }

    private void loadedDepart(Depart depart){
        this.depart = depart;
    }

    private void updatedDepart(Depart depart){

    }

    private void fetchDepart() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedDepart", Depart.class);
        String departId = null;
        Depart temp = new Depart();
        temp.setId(departId);

        loadHelper.loadData(departService.fetchDepart(temp));
    }

    private void saveDepart() {
        LoadHelper loadHelper = new LoadHelper(this, "updatedDepart", Depart.class);

        loadHelper.loadData(departService.saveDepart(depart));
    }
}