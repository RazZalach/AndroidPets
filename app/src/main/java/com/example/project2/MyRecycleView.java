package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRecycleView extends AppCompatActivity {
    DbDiary dbDiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycle_view);
        dbDiary = new DbDiary(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        List<DiaryPage> items = dbDiary.readAllDiaryPages();

        //String name, String body, Date dateCreated, int PicId

        if(items.size() > 0){
            recyclerView.setLayoutManager( new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));
        }

    }
}