package com.example.chara.activity.passport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chara.R;
import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Passport;
import com.example.chara.service.PassportService;

import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class PassportActivity extends AppCompatActivity {

    private List<Passport> passports;

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private PassportService passportService = retrofit.create(PassportService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_passport);
        fio = findViewById(R.id.tvFIO);
        bd = findViewById(R.id.tvBD);
        gender = findViewById(R.id.tvGender);
        passportNumber = findViewById(R.id.tvPassportNumber);
        passportIssued = findViewById(R.id.tvPassportIssued);
        passportDepartment = findViewById(R.id.tvPassportDepartment);

        Employee employee = (Employee) getIntent().getSerializableExtra("employee");
        Passport passport = employee.getPassport();
        displayPassportDetails(employee, passport);
    }
    private TextView fio, bd, gender, passportNumber, passportIssued, passportDepartment;
    private void displayPassportDetails(Employee employee, Passport passport) {
        String name = employee.getName();
        String surname = employee.getSurname();
        String patronymic = employee.getPatronymic();
        String dob = employee.getBorn() != null ? employee.getBorn().toString() : null;
        Boolean res = employee.getSex();
        String genderTxt = null;
        if (res != null)
            genderTxt = res ? "мужской" : "женский";
        String passportNum = null, passportIss = null, passportDep = null;
        if (passport != null) {
            passportNum = passport.getNumber();
            passportIss = passport.getIssued() != null ? passport.getIssued().toString() : null;
            passportDep = passport.getDepartmentCode();
        }
        fio.setText((name == null || surname == null || patronymic == null ? "ФИО: Нет данных" : name + " " + surname + " " + patronymic));
        Date dt = new Date(dob);
        bd.setText((dob == null) ? "Дата рождения: Нет данных" : "Дата рождения: " + dt.getDay() + " " + dt.getMonth() + " " + dt.getYear());
        gender.setText((genderTxt == null) ? "Пол: Нет данных" : "Пол: " + genderTxt);
        passportNumber.setText((passportNum == null) ? "Номер паспорта: Нет данных" : "Номер паспорта: " + passportNum);
        passportIssued.setText((passportIss == null) ? "Дата выдачи: Нет данных" : "Дата выдачи: " + passportIss);
        passportDepartment.setText((passportDep == null) ? "Код подразделения: Нет данных" : "Код подразделения: " + passportDep);
        // Установка фотографии соискателя (если есть)
//        if (seeker.getPhotoUrl() != null) {
//            // Загрузка изображения из URL или из ресурсов
//        }
    }

    private void loadedPassports(List<Passport> passports){ this.passports = passports; }

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