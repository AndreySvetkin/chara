package com.example.chara.activity.user;

import static com.example.chara.DatabaseManager.checkIfLoginExists;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.DatabaseManager;
import com.example.chara.activity.MainActivity;
import com.example.chara.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Registration extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPassword, etConfirmPassword, etDOB;
    private Button btnUploadAvatar, btnTakePhoto, btnRegister, btnSelectDOB;

    private byte[] avatarByteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etDOB = findViewById(R.id.et_dob);
        btnUploadAvatar = findViewById(R.id.btn_upload_avatar);
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnRegister = findViewById(R.id.btn_register);
        btnSelectDOB = findViewById(R.id.btn_select_dob);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == Activity.RESULT_OK && data != null) {
                            Uri selectedImage = data.getData();
                            try {
                                Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(Registration.this.getContentResolver(), selectedImage));
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                ImageView avatarImageView = findViewById(R.id.avatar);
                                avatarImageView.setImageBitmap(bitmap);
                                avatarByteArray = byteArray;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        btnSelectDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        etDOB.setText(selectedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(pickPhoto);
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Камера недоступна", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String dob = etDOB.getText().toString();


                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                        password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dob.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Введите дату рождения", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                Calendar currentDate = Calendar.getInstance();
                Calendar dobDate = Calendar.getInstance();
                try {
                    dobDate.setTime(sdf.parse(dob));
                }
                catch (java.text.ParseException e) {
                    return;
                }

                if (dobDate.after(currentDate)) {
                    Toast.makeText(getApplicationContext(), "Дата рождения не может быть в будущем", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentDate.add(Calendar.YEAR, -6); // Minimum age of 6 years
                if (dobDate.after(currentDate)) {
                    Toast.makeText(getApplicationContext(), "Пользователь должен быть не моложе 6 лет", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!checkIfLoginExists(username)) {
                    if (!DatabaseManager.registerUser(firstName, lastName, username, password, dob, avatarByteArray))
                        Toast.makeText(getApplicationContext(), "Возникли проблемы с базой данных", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Логин уже занят", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // Отображаем изображение
            ImageView avatarImageView = findViewById(R.id.avatar);
            avatarImageView.setImageBitmap(imageBitmap);

            avatarByteArray = byteArray;
        }
    }

}