package com.example.chara.activity.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;
import com.example.chara.activity.passport.PassportActivity;
import com.example.chara.activity.seeker.SeekerProfile;
import com.example.chara.model.Employee;
import com.example.chara.model.Passport;

public class EmployeeInfoActivity extends AppCompatActivity {

    private TextView fioTextView, phoneTextView, addressTextView, chiefTextView, departTextView, postTextView, passportsTextView;
    private ImageView imgView;
    private ImageButton btnDocuments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_employee);
        imgView = findViewById(R.id.employeePhoto);
        fioTextView = findViewById(R.id.textFIO);
        phoneTextView = findViewById(R.id.textPhone);
        addressTextView = findViewById(R.id.textAddress);
        chiefTextView = findViewById(R.id.textChief);
        departTextView = findViewById(R.id.textDepart);
        postTextView = findViewById(R.id.textPost);
        passportsTextView = findViewById(R.id.textPassport);
        btnDocuments = findViewById(R.id.btnDocuments);

        // Получение данных о работнике из Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("employee")) {
            Employee employee = (Employee) intent.getSerializableExtra("employee");
            if (employee != null) {
                btnDocuments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EmployeeInfoActivity.this, PassportActivity.class);
                        intent.putExtra("employee", employee);
                        startActivity(intent);
                    }
                });
                displayEmployeeDetails(employee);
            } else {
                Toast.makeText(this, "Нет данных о работнике", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Нет данных о работнике", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        fioTextView.setText("ФИО: " + employee.getName() + " " + employee.getSurname() + " " + employee.getPatronymic());
        if (employee.getPhone() != null) {
            phoneTextView.setText(employee.getPhone());
        } else {
            phoneTextView.setText("Телефон: Нет данных");
        }
        if (employee.getAddress() != null) {
            addressTextView.setText("Адрес: " + employee.getAddress());
        } else {
            addressTextView.setText("Адрес: Нет данных");
        }
        if (employee.getChief() != null) {
            chiefTextView.setText("Начальник: " + employee.getChief().getName() + " " + employee.getChief().getSurname() + " " +employee.getChief().getPatronymic());
        } else {
            chiefTextView.setText("Начальник: Нет данных");
        }
        if (employee.getDepart() != null) {
            departTextView.setText("Отдел: " + employee.getDepart().getName());
        } else {
            departTextView.setText("Отдел: Нет данных");
        }
        if (employee.getPost() != null) {
            postTextView.setText("Должность: " + employee.getPost().getName());
        } else {
            postTextView.setText("Должность: Нет данных");
        }
        if (employee.getPassport() != null) {
            passportsTextView.setText("Паспорт: " + employee.getPassport());
        } else {
            passportsTextView.setText("Паспорт: Нет данных");
        }
    }
}