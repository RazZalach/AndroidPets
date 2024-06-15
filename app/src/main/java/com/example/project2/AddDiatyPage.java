package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Date;

public class AddDiatyPage extends AppCompatActivity {

    EditText etName , etBody , etDateCreated , etPicId;
    Button btnSave;
    DbDiary dbDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diaty_page);
        dbDiary = new DbDiary(getApplicationContext());
        TextView drawableIdsTextView = findViewById(R.id.drawable_ids_textview);
        String drawableIds = getDrawableResourceIds(this);

        drawableIdsTextView.setText(drawableIds);

        etPicId = findViewById(R.id.etPicId);
        etName = findViewById(R.id.etName);
        etBody = findViewById(R.id.etBody);
        etDateCreated = findViewById(R.id.etDateCreated);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etPicurl = etPicId.getText().toString();
               String stringetName = etName.getText().toString();
               String stringetBody = etName.getText().toString();
               String stringetDateCreated = etDateCreated.getText().toString();
               dbDiary.addDiaryPage(new DiaryPage(stringetName, stringetBody,etPicurl));
                Toast.makeText(getApplicationContext(),"Diary Page Added",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MyRecycleView.class);
                startActivity(intent);
            }
        });

    }
    private String getDrawableResourceIds(Context context) {
        StringBuilder drawableIds = new StringBuilder();
        Field[] drawables = R.drawable.class.getFields();

        for (Field drawable : drawables) {
            try {
                int resourceId = drawable.getInt(null);
                String resourceName = drawable.getName();
                drawableIds.append(resourceName)
                        .append(" = ")
                        .append(resourceId)
                        .append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return drawableIds.toString();
    }
}