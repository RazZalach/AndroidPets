package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DiaryPageActivity extends AppCompatActivity {

    DbDiary dbDiary;
    EditText editTextName;
    EditText editTextBody;
    TextView textViewDateCreated;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                finish();
            }
        });
        Button saveChangesButton = findViewById(R.id.saveChangesButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
        dbDiary = new DbDiary(getApplicationContext());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int diaryId = sharedPreferences.getInt("diary_id", -1); // -1 is the default value if the key is not found

        editTextName = findViewById(R.id.editTextName);
        editTextBody = findViewById(R.id.editTextBody);
        textViewDateCreated = findViewById(R.id.textViewDateCreated);

        if (diaryId != -1) {
            DiaryPage diaryPage = dbDiary.getDiaryPageById(diaryId);
            if (diaryPage != null) {
                // Fill the fields with data from the DiaryPage object
                editTextName.setText(diaryPage.getName());
                editTextBody.setText(diaryPage.getBody());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String formattedDate = dateFormat.format(diaryPage.getDateCreated());
                textViewDateCreated.setText("Date Created: " + formattedDate);
                // Load image from file path
                File imgFile = new File(diaryPage.getPicUrl());
                ImageView imageView = findViewById(R.id.imageViewPic);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                } else {
                    // Set a default image if the file doesn't exist
                    imageView.setImageResource(R.drawable.baseline_person_24);
                }

            } else {
                // Handle the case where the diary page with the retrieved ID is not found
            }
        } else {
            // Handle the case where the diary ID is not found in SharedPreferences
        }
    }
    private void saveChanges() {
        String newName = editTextName.getText().toString();
        String newBody = editTextBody.getText().toString();
        int diaryId = getSharedPreferences("MyPrefs", MODE_PRIVATE).getInt("diary_id", -1);
        if (diaryId != -1) {
            if (dbDiary.updateDiaryById(diaryId, newName, newBody)) {
                // Successfully updated the diary page
                Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                // You can add additional actions here if needed
            } else {
                // Failed to update the diary page
                Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                // You can add additional error handling here if needed
            }
        } else {
            // Diary ID not found in SharedPreferences
            Toast.makeText(this, "Diary ID not found", Toast.LENGTH_SHORT).show();
            // You can add additional error handling here if needed
        }
    }
}
