package com.example.chara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Depart;
import com.example.chara.model.Passport;
import com.example.chara.service.DepartService;
import com.example.chara.service.PassportService;

import retrofit2.Retrofit;

public class PassportEditActivity extends AppCompatActivity {

    private Passport passport;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private PassportService passportService = retrofit.create(PassportService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_edit);
    }

    private void loadedPassport(Passport passport){
        this.passport = passport;
    }

    private void updatedPassport(Passport passport){

    }

    private void fetchPassport() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedPassport", Passport.class);
        String passportId = null;
        Passport temp = new Passport();
        temp.setId(passportId);

        loadHelper.loadData(passportService.fetchPassport(temp));
    }

    private void savePassport() {
        LoadHelper loadHelper = new LoadHelper(this, "updatedPassport", Passport.class);

        loadHelper.loadData(passportService.savePassport(passport));
    }
}