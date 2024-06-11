package com.example.chara.activity.seeker;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chara.config.AppConfig;
import com.example.chara.helper.LoadHelper;
import com.example.chara.model.Employee;
import com.example.chara.model.Interview;
import com.example.chara.model.Resume;
import com.example.chara.service.InterviewService;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Retrofit;

public class SeekerProfile extends AppCompatActivity {

    private TextView titleTextView, fioTextView, birthDateTextView, maritalStatusTextView, phoneTextView, emailTextView;
    private TextView educationTextView, experienceTextView, professionalSkillsTextView, personalSkillsTextView, additionalInfoTextView;
    private ImageView photoImageView;
    private ImageButton btnInterview;
    private Calendar selectedDateTime = Calendar.getInstance();

    private Retrofit retrofit = AppConfig.getRetrofitInstance();

    private InterviewService InterviewService = retrofit.create(InterviewService.class);
    private Resume seeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_seeker);

        // Связываем поля XML с соответствующими TextView
        titleTextView = findViewById(R.id.textTitleVacancy);
        fioTextView = findViewById(R.id.textFIO);
        birthDateTextView = findViewById(R.id.textBirthDate);
        maritalStatusTextView = findViewById(R.id.textMaritalStatus);
        phoneTextView = findViewById(R.id.textPhone);
        emailTextView = findViewById(R.id.textEmail);
        educationTextView = findViewById(R.id.tvEducation);
        experienceTextView = findViewById(R.id.tvExperience);
        professionalSkillsTextView = findViewById(R.id.tvProfessionalSkills);
        personalSkillsTextView = findViewById(R.id.tvPersonalSkills);
        additionalInfoTextView = findViewById(R.id.tvAdditionalInfo);
        photoImageView = findViewById(R.id.imagePhoto);
        btnInterview = findViewById(R.id.btnInterview);

        // Получение данных о соискателе из Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("resume")) {
            seeker = (Resume) intent.getSerializableExtra("resume");
            if (seeker != null) {
                displaySeekerDetails(seeker);
            } else {
                Toast.makeText(this, "Нет данных о соискателе", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Нет данных о соискателе", Toast.LENGTH_SHORT).show();
        }

        // Устанавливаем обработчик нажатия на кнопку btnInterview
        btnInterview.setOnClickListener(v -> showInviteDialog());
    }

    private void displaySeekerDetails(Resume seeker) {
        String dob = "Нет данных";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dob = dateFormat.format(seeker.getBorn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Заполняем поля данными из объекта Seeker
        titleTextView.setText("Вакансия: " + (seeker.getVacancy() != null ? seeker.getVacancy().getName() : "Не указано"));
        fioTextView.setText("ФИО: " + (seeker.getFio() != null ? seeker.getFio() : "Нет данных"));
        birthDateTextView.setText("Дата рождения: " + dob);
        maritalStatusTextView.setText("Семейное положение: " + (seeker.getMarried() == null ? "Нет данных" : seeker.getMarried()));
        phoneTextView.setText("Телефон: " + (seeker.getPhone() == null ? "Нет данных" : seeker.getPhone()));
        emailTextView.setText("Email: " + (seeker.getEmail() == null ? "Нет данных" : seeker.getEmail()));
        educationTextView.setText("Образование:\n" + (seeker.getEducation() == null ? "Нет данных" : seeker.getEducation()));
        experienceTextView.setText("Опыт работы: " + (seeker.getExperience() == null ? "Нет данных" : seeker.getExperience()));
        professionalSkillsTextView.setText("Профессиональные навыки: " + (seeker.getHardSkills() == null ? "Нет данных" : seeker.getHardSkills()));
        personalSkillsTextView.setText("Личные навыки: " + (seeker.getSoftSkills() == null ? "Нет данных" : seeker.getSoftSkills()));
        additionalInfoTextView.setText("Дополнительная информация: " + (seeker.getAdditionalInfo() == null ? "Нет данных" : seeker.getAdditionalInfo()));
        // Установка фотографии соискателя (сделать)
    }

    private void showInviteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Приглашение на собеседование");
        View dialogView = getLayoutInflater().inflate(R.layout.dialogue_invite, null);
        builder.setView(dialogView);
        TextView tvDateTime = dialogView.findViewById(R.id.tvDateTime);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnInvite = dialogView.findViewById(R.id.btnInvite);
        tvDateTime.setOnClickListener(v -> showDateTimePicker(tvDateTime));
        AlertDialog dialog = builder.create();
        dialog.show();
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnInvite.setOnClickListener(v -> {
            if (selectedDateTime.getTimeInMillis() < System.currentTimeMillis()) {
                Toast.makeText(this, "Выберите будущую дату и время", Toast.LENGTH_SHORT).show();
            } else {
                // Отправка данных о дате и резюме
                sendInvite(selectedDateTime, seeker);
                dialog.dismiss();
            }
        });
    }

    private void showDateTimePicker(TextView tvDateTime) {
        // Получаем текущую дату
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Создаем DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedDateTime.set(Calendar.YEAR, selectedYear);
                    selectedDateTime.set(Calendar.MONTH, selectedMonth);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, selectedDay);

                    // После выбора даты показываем TimePickerDialog
                    showTimePicker(tvDateTime);
                }, year, month, day);

        // Ограничиваем выбор даты текущей датой и позже
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePicker(TextView tvDateTime) {
        // Получаем текущие время
        int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);

        // Создаем TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedDateTime.set(Calendar.MINUTE, selectedMinute);

                    // Форматируем дату и время для отображения
                    String formattedDateTime = String.format("%02d-%02d-%04d %02d:%02d",
                            selectedDateTime.get(Calendar.DAY_OF_MONTH),
                            selectedDateTime.get(Calendar.MONTH) + 1,
                            selectedDateTime.get(Calendar.YEAR),
                            selectedHour, selectedMinute);
                    tvDateTime.setText(formattedDateTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void sendInvite(Calendar dateTime, Resume seeker) {
        // Логика для отправки приглашения, например, отправка по email или push-уведомлению
        addInterview(new Interview(dateTime.getTime(), seeker, null));
        Toast.makeText(this, "Приглашение на собеседование отправлено на " + dateTime.getTime().toString() + " для соискателя " + seeker.getFio(), Toast.LENGTH_SHORT).show();
    }

    public void uploadedInterview(Interview interview){
    }

    public void addInterview(Interview interview) {
        LoadHelper loadHelper = new LoadHelper(this, "uploadedInterview", Interview.class);
        loadHelper.loadData(InterviewService.addInterview(interview));
    }
}