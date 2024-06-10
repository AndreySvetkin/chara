package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Employee;
import com.example.chara.service.EmployeeService;

import java.util.List;

import retrofit2.Retrofit;

public class EmployeeActivity extends AppCompatActivity {

    private List<Employee> employees;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private EmployeeService employeeService = retrofit.create(EmployeeService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
    }

    private void loadedEmployees(List<Employee> employees){
        this.employees = employees;
    }

    private void uploadedEmployee(Employee employee){

    }

    private void deletedEmployee(Employee employee){

    }

    private void allEmployees() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedEmployees", List.class);

        loadHelper.loadData(employeeService.allEmployees());
    }

    private void addEmployee(Employee employee) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedEmployee", Employee.class);

        loadHelper.loadData(employeeService.addEmployee(employee));
    }

    private void deleteEmployee(Employee employee) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedEmployee", Employee.class);

        loadHelper.loadData(employeeService.deleteEmployee(employee));
    }
}