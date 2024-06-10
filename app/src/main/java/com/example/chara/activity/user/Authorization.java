package com.example.chara.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.DatabaseManager;
import com.example.chara.activity.MainActivity;
import com.example.chara.R;


public class Authorization extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    SharedPreferences sharedPreferences;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        loginEditText = findViewById(R.id.login_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Проверка логина и пароля в базе данных
                if (DatabaseManager.checkLogin(login, password)) {
                    // Если данные верны, перейти на MainActivity
                    Editor editor = sharedPreferences.edit();
                    editor.putString("username", login);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent(Authorization.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Если данные неверны, вывести сообщение об ошибке
                    Toast.makeText(Authorization.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authorization.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}