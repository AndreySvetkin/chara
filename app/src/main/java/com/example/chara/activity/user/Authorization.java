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

import com.example.chara.activity.MainActivity;
import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Profile;
import com.example.chara.service.UserService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class Authorization extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;

    private List<Profile> profiles;
    SharedPreferences sharedPreferences;
    private EditText loginEditText;
    private EditText passwordEditText;
    private Retrofit retrofit = AppConfig.getRetrofitInstance();
    private UserService userService =  retrofit.create(UserService.class);

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
                if (getUserOnString(login, password) != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putString("username", login);
                    editor.putString("password", password);
                    Intent intent = new Intent(Authorization.this, MainActivity.class);
                    startActivity(intent);
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

    public void createdToken(Map<String,String> map) {
        UserServiceHelper.ACCESS_TOKEN = map.get("access_token");
    }

    public void getToken(String login, String password) {
        LoadHelper loadHelper = new LoadHelper(this, "loadedToken", Map.class);

        loadHelper.loadData(userService.getToken(login, password));
    }



    Profile getUserOnString(String login, String password){
        return profiles.stream()
                .filter(profile -> {
                    return profile.getLogin().equals(login) && profile.getPassword().equals(password);
                }).findFirst().orElse(null);

    }
    public void loadedUsers(List<Profile> profiles){
        this.profiles = profiles;
    }

    private void allUsers() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedUsers", Profile.class);

        loadHelper.loadData(userService.allUsers());
    }
}