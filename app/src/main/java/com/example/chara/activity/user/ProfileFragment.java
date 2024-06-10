package com.example.chara.activity.user;

import static com.example.chara.DatabaseManager.checkPassword;
import static com.example.chara.DatabaseManager.updatePassword;
import static com.example.chara.DatabaseManager.updateUserProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chara.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ProfileFragment extends Fragment {
    private boolean isChangePasswordVisible = false;
    private boolean isChangeAvatarVisible = false;
    Uri image;
    byte[] imageBytes;
    ImageView avatarImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatarImageView = view.findViewById(R.id.avatarImageView);
        EditText firstNameEditText = view.findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = view.findViewById(R.id.lastNameEditText);
        EditText birthDateEditText = view.findViewById(R.id.birthDateEditText);
        EditText currentPassEditText = view.findViewById(R.id.currentPasswordEditText);
        EditText newPassEditText = view.findViewById(R.id.newPasswordEditText);

        Button takePhotoButton = view.findViewById(R.id.photoButton);
        Button editButton = view.findViewById(R.id.editButton);
        Button dobEditButton = view.findViewById(R.id.btn_select_dob);
        Button changeAvatarButton = view.findViewById(R.id.changeAvatarButton);
        Button changePasswordButton = view.findViewById(R.id.changePasswordButton);
        LinearLayout changePasswordLayout = view.findViewById(R.id.changePasswordLayout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
//        Profile profile = getUserProfile(username);
//        if (profile != null) {
//            firstNameEditText.setText(profile.getFirstName());
//            lastNameEditText.setText(profile.getLastName());
//            birthDateEditText.setText(profile.getBirthDate());
//            byte[] avatar = profile.getAvatar();
//            imageBytes = avatar;
//            if (avatar != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
//                avatarImageView.setImageBitmap(bitmap);
//            }
//        }
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
                        Toast.makeText(getActivity(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String dob = birthDateEditText.getText().toString();
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
                        Toast.makeText(getContext(), "Дата рождения не может быть в будущем", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentDate.add(Calendar.YEAR, -6); // Minimum age of 6 years
                    if (dobDate.after(currentDate)) {
                        Toast.makeText(getContext(), "Пользователь должен быть не моложе 6 лет", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    byte[] avatarBytes;
                    if (imageBytes != null && image == null) {
                        avatarBytes = imageBytes;
                    }
                    else if (image == null) avatarBytes = null;
                    else try {
                        Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getActivity().getContentResolver(), image));
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        avatarBytes= stream.toByteArray();
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
                // Get current date
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create and show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Камера недоступна", Toast.LENGTH_SHORT).show();
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

        Button confirmPasswordButton = view.findViewById(R.id.confirmPasswordButton);
        confirmPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPassEditText.getText().toString();
                String newPassword = newPassEditText.getText().toString();
                if (newPassword.length() < 4) {
                    Toast.makeText(getContext(), "Пароль слишком короткий", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Проверка текущего пароля
                boolean isPasswordCorrect = checkPassword(username, currentPassword);

                if (isPasswordCorrect) {
                    // Обновление нового пароля
                    boolean success = updatePassword(username, newPassword);
                    if (success) {
                        Toast.makeText(getContext(), "Пароль успешно обновлен", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Ошибка при обновлении пароля", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Введен неверный текущий пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }



    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    private void updateProfile(String username, String firstName, String lastName, String birthDate, byte[] avatar) {
        boolean success = updateUserProfile(username, firstName, lastName, birthDate, avatar);
        if (!success) {
            Toast.makeText(getContext(), "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            image = imageUri;
            avatarImageView.setImageURI(imageUri);
        }
        if (requestCode == 1 && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();
            // Отображаем изображение
            avatarImageView.setImageBitmap(imageBitmap);
        }
    }

}