package com.example.chara.activity.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chara.R;
import com.example.chara.adapter.EmployeeAdapter;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class EmployeeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employees;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private EmployeeService employeeService = retrofit.create(EmployeeService.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_employees);
        recyclerView = findViewById(R.id.recycleViewEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allEmployees();
    }


    private void loadEmployees() {

        employeeAdapter.notifyDataSetChanged();
    }

    public void loadedEmployees(List<Employee> employees)
    {
        System.out.println(employees);
        this.employees = employees;
        employeeAdapter = new EmployeeAdapter(this, this.employees, new EmployeeAdapter.OnEmployeeClickListener() {
            @Override
            public void onEmployeeClick(int position) {
                Employee employee = employees.get(position);
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeInfoActivity.class);
                intent.putExtra("employee", employee);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(employeeAdapter);
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