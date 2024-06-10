package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.service.EmployeeService;

import java.util.List;

import retrofit2.Retrofit;

public class EmployeeEditActivity extends AppCompatActivity {

    private Employee employee;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private EmployeeService employeeService = retrofit.create(EmployeeService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_edit);
    }

    private void loadedEmployee(Employee employee){
        this.employee = employee;
    }

    private void updatedEmployee(Employee employee){

    }

    private void fetchEmployee(Employee employee) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedEmployee", Employee.class);

        loadHelper.loadData(employeeService.fetchEmployee(employee));
    }

    private void saveEmployee() {
        LoadHelper loadHelper = new LoadHelper(this, "updatedEmployee", Employee.class);

        loadHelper.loadData(employeeService.saveEmployee(employee));
    }
}