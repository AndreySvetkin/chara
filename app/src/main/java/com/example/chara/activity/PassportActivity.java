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

import java.util.List;

import retrofit2.Retrofit;

public class PassportActivity extends AppCompatActivity {

    private List<Passport> passports;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private PassportService passportService = retrofit.create(PassportService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
    }

    private void loadedPassports(List<Passport> passports){
        this.passports = passports;
    }

    private void uploadedPassport(Passport passport){

    }

    private void deletedPassport(Passport passport){

    }

    private void allPassports() {
        LoadHelper loadHelper = new LoadHelper(this, "loadedPassports", List.class);

        loadHelper.loadData(passportService.allPassports());
    }

    private void addPassport(Passport passport) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedPassport", Passport.class);

        loadHelper.loadData(passportService.addPassport(passport));
    }

    private void deletePassport(Passport passport) {
        LoadHelper loadHelper = new LoadHelper(this, "deletedPassport", Passport.class);

        loadHelper.loadData(passportService.deletePassport(passport));
    }
}