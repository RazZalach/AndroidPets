package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class FarmDetialsActivity extends AppCompatActivity {

    DbFarm db;
    TextView farmNameTextView;
    Button backButton, reset_hunger_btn;
    HorseAdapter horseAdapter;
    RecyclerView recyclerView;
    long farmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_detials);
        db = new DbFarm(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        farmId = Long.parseLong(sharedPreferences.getString("farm_id", "-1"));
        recyclerView = findViewById(R.id.recyclerview_horse);
        List<Horse> items = db.getHorsesForFarm(farmId);
        if (items != null && !items.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            horseAdapter = new HorseAdapter(this, items);
            recyclerView.setAdapter(horseAdapter);
        } else {
            Log.d("raz", "No diary pages found or items is null");
        }
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back to the previous activity
            }
        });
        reset_hunger_btn = findViewById(R.id.reset_hunger_btn);
        reset_hunger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setRandomHungerForHorses(farmId);
                refreshHorseList();
            }
        });
    }

    private void refreshHorseList() {
        List<Horse> updatedItems = db.getHorsesForFarm(farmId);
        horseAdapter.updateData(updatedItems);
    }
}
