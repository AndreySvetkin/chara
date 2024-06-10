package com.example.chara.activity.user;

import static com.example.chara.DatabaseManager.checkPassword;
import static com.example.chara.DatabaseManager.updatePassword;
import static com.example.chara.DatabaseManager.updateUserProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private boolean isChangePasswordVisible = false;
    private boolean isChangeAvatarVisible = false;
    Uri image;
    byte[] imageBytes;
    ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImageView = findViewById(R.id.avatarImageView);
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText birthDateEditText = findViewById(R.id.birthDateEditText);
        EditText currentPassEditText = findViewById(R.id.currentPasswordEditText);
        EditText newPassEditText = findViewById(R.id.newPasswordEditText);

        Button takePhotoButton = findViewById(R.id.photoButton);
        Button editButton = findViewById(R.id.editButton);
        Button dobEditButton = findViewById(R.id.btn_select_dob);
        Button changeAvatarButton = findViewById(R.id.changeAvatarButton);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        LinearLayout changePasswordLayout = findViewById(R.id.changePasswordLayout);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChangeAvatarVisible) {
                    changeAvatarButton.setVisibility(View.GONE);
                    takePhotoButton.setVisibility(View.GONE);
                    dobEditButton.setVisibility(View.INVISIBLE);
                    firstNameEditText.setEnabled(false);
                    lastNameEditText.setEnabled(false);
                    isChangeAvatarVisible = false;

                    if (firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || birthDateEditText.getText().toString().isEmpty()) {
                        Toast.makeText(ProfileActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String dob = birthDateEditText.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar currentDate = Calendar.getInstance();
                    Calendar dobDate = Calendar.getInstance();
                    try {
                        dobDate.setTime(sdf.parse(dob));
                    } catch (java.text.ParseException e) {
                        return;
                    }
                    if (dobDate.after(currentDate)) {
                        Toast.makeText(ProfileActivity.this, "Дата рождения не может быть в будущем", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentDate.add(Calendar.YEAR, -6); // Minimum age of 6 years
                    if (dobDate.after(currentDate)) {
                        Toast.makeText(ProfileActivity.this, "Пользователь должен быть не моложе 6 лет", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    byte[] avatarBytes;
                    if (imageBytes != null && image == null) {
                        avatarBytes = imageBytes;
                    } else if (image == null) avatarBytes = null;
                    else try {
                            Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), image));
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                            avatarBytes = stream.toByteArray();
                        } catch (java.io.IOException e) {
                            avatarBytes = new byte[]{};
                        }

                    // Обновление данных в базе данных
                    updateProfile(username, firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), birthDateEditText.getText().toString(), avatarBytes);
                } else {
                    changeAvatarButton.setVisibility(View.VISIBLE);
                    takePhotoButton.setVisibility(View.VISIBLE);
                    dobEditButton.setVisibility(View.VISIBLE);
                    firstNameEditText.setEnabled(true);
                    lastNameEditText.setEnabled(true);
                    isChangeAvatarVisible = true;
                }
            }
        });

        dobEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        birthDateEditText.setText(selectedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                } else {
                    Toast.makeText(ProfileActivity.this, "Камера недоступна", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChangePasswordVisible) {
                    changePasswordLayout.setVisibility(View.GONE);
                    isChangePasswordVisible = false;
                } else {
                    changePasswordLayout.setVisibility(View.VISIBLE);
                    isChangePasswordVisible = true;
                }
            }
        });

        Button confirmPasswordButton = findViewById(R.id.confirmPasswordButton);
        confirmPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPassEditText.getText().toString();
                String newPassword = newPassEditText.getText().toString();
                if (newPassword.length() < 4) {
                    Toast.makeText(ProfileActivity.this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isPasswordCorrect = checkPassword(username, currentPassword);

                if (isPasswordCorrect) {
                    boolean success = updatePassword(username, newPassword);
                    if (success) {
                        Toast.makeText(ProfileActivity.this, "Пароль успешно обновлен", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Ошибка при обновлении пароля", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Введен неверный текущий пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    private void updateProfile(String username, String firstName, String lastName, String birthDate, byte[] avatar) {
        boolean success = updateUserProfile(username, firstName, lastName, birthDate, avatar);
        if (!success) {
            Toast.makeText(ProfileActivity.this, "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            image = imageUri;
            avatarImageView.setImageURI(imageUri);
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();
            avatarImageView.setImageBitmap(imageBitmap);
        }
    }
}