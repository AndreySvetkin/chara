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

    private List<Employee> createFakeEmployees() {
        List<Employee> fakeEmployees = new ArrayList<>();
        fakeEmployees.add(new Employee("1", "John", "Doe", 60000.0, "Jr.", "1234567890", "123 Street"));
        fakeEmployees.add(new Employee("2", "Jane", "Smith", 70000.0, "M.", "1234567891", "456 Avenue"));
        fakeEmployees.add(new Employee("3", "Bob", "Johnson", 50000.0, "Sr.", "1234567892", "789 Boulevard"));
        fakeEmployees.add(new Employee("4", "Emily", "Davis", 80000.0, "Jr.", "1234567893", "101 Pine Street"));
        fakeEmployees.add(new Employee("5", "Mike", "Wilson", 55000.0, "Sr.", "1234567894", "202 Oak Avenue"));
        fakeEmployees.add(new Employee("6", "Sarah", "Brown", 75000.0, "M.", "1234567895", "303 Maple Boulevard"));
        fakeEmployees.add(new Employee("7", "David", "Anderson", 65000.0, "Jr.", "1234567896", "404 Elm Street"));
        fakeEmployees.add(new Employee("8", "Laura", "Martinez", 72000.0, "Sr.", "1234567897", "505 Birch Avenue"));
        fakeEmployees.add(new Employee("9", "Chris", "Garcia", 68000.0, "M.", "1234567898", "606 Pine Boulevard"));
        fakeEmployees.add(new Employee("10", "Maria", "Lopez", 59000.0, "Jr.", "1234567899", "707 Oak Street"));
        fakeEmployees.add(new Employee("11", "Matt", "Taylor", 63000.0, "Sr.", "1234567800", "808 Maple Avenue"));
        fakeEmployees.add(new Employee("12", "Amy", "Scott", 71000.0, "M.", "1234567801", "909 Elm Boulevard"));
        fakeEmployees.add(new Employee("13", "Kevin", "Adams", 54000.0, "Jr.", "1234567802", "111 Pine Street"));
        fakeEmployees.add(new Employee("14", "Rachel", "Thomas", 59000.0, "Sr.", "1234567803", "222 Oak Avenue"));
        fakeEmployees.add(new Employee("15", "Daniel", "Allen", 78000.0, "M.", "1234567804", "333 Maple Boulevard"));
        return fakeEmployees;
    }


    private void loadEmployees() {

        employeeAdapter.notifyDataSetChanged();
    }

    private void loadedEmployees(List<Employee> employees)
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