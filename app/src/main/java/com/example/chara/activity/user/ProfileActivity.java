package com.example.chara.activity.user;

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

        Button takePhotoButton = findViewById(R.id.photoButton);
        Button editButton = findViewById(R.id.editButton);
        Button changeAvatarButton = findViewById(R.id.changeAvatarButton);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChangeAvatarVisible) {
                    changeAvatarButton.setVisibility(View.GONE);
                    takePhotoButton.setVisibility(View.GONE);
                    firstNameEditText.setEnabled(false);
                    lastNameEditText.setEnabled(false);
                    isChangeAvatarVisible = false;

                    if (firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty()) {
                        Toast.makeText(ProfileActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
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

                }
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
}}