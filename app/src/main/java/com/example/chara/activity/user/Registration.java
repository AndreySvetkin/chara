package com.example.chara.activity.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;
import com.example.chara.activity.interview.InterviewDecisionActivity;
import com.example.chara.activity.interview.InterviewListActivity;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Profile;
import com.example.chara.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import retrofit2.Retrofit;

public class Registration extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private Retrofit retrofit = AppConfig.getRetrofitInstance();
    private UserService userService =  retrofit.create(UserService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();


                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                        password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    return;
                }


                createUser(new Profile(username, password, lastName, firstName));

            }
        });
    }

    public void createdUser(){
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }
    private void createUser(Profile profile) {
        LoadHelper loadHelper = new LoadHelper(this, "createUser", Profile.class);

        loadHelper.loadData(userService.createUser(profile));
    }

}

