package com.example.chara;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;

public class TagActivity extends AppCompatActivity {
    EditText tagEditText;
    Spinner tagSpinner;
    Button saveButton, deleteButton;
 //   DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

       // dbHelper = new DBHelper(this);

        tagEditText = findViewById(R.id.tagEditText);
        tagSpinner = findViewById(R.id.tagSpinner);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String tagName = tagEditText.getText().toString();
//                if (tagName.trim().isEmpty()) {
//                    Toast.makeText(TagActivity.this, "Please enter a tag name", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (checkIfTagExists(tagName)) {
//                        Toast.makeText(TagActivity.this, "Tag already exists", Toast.LENGTH_SHORT).show();
//                    } else {
//                      //  dbHelper.addTag(tagName);
//                        Toast.makeText(TagActivity.this, "Tag saved successfully", Toast.LENGTH_SHORT).show();
//                        tagEditText.setText("");
//                        loadTags();
//                    }
//                }
//            }
//        });

//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String tagName = tagSpinner.getSelectedItem().toString();
//                if (tagName.equals("")) {
//                    Toast.makeText(TagActivity.this, "Please select a tag to delete", Toast.LENGTH_SHORT).show();
//                } else {
//                  //  if (checkIfTagUsed(tagName)) {
//                        Toast.makeText(TagActivity.this, "Tag is currently used in notes, cannot delete", Toast.LENGTH_SHORT).show();
//                    } else {
//                      //  dbHelper.deleteTag(tagName);
//                        Toast.makeText(TagActivity.this, "Tag deleted successfully", Toast.LENGTH_SHORT).show();
//                       // loadTags();
//                    }
//                }
//            }
//        });

       // loadTags();
    }

//    private boolean checkIfTagExists(String tagName) {
//        return dbHelper.checkIfTagExists(tagName);
//    }
//
//    private boolean checkIfTagUsed(String tagName) {
//        return dbHelper.checkIfTagUsed(tagName);
//    }
//
//    private void loadTags() {
//        List<String> tags = dbHelper.getAllTags();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tags);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        tagSpinner.setAdapter(adapter);
//    }
}