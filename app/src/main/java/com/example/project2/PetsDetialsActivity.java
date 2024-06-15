package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PetsDetialsActivity extends AppCompatActivity {
    private ImageView imageViewPetDetails;
    private TextView textViewNameDetails, textViewTypeDetails, textViewAgeDetails, textViewColorDetails, textViewIsAdoptedDetails;
    private  Button contact_my;
    private PetsDataBase petsDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_detials);
        petsDataBase = new PetsDataBase(getApplicationContext());
        imageViewPetDetails = findViewById(R.id.imageViewPetDetails);
        textViewNameDetails = findViewById(R.id.textViewNameDetails);
        textViewTypeDetails = findViewById(R.id.textViewTypeDetails);
        textViewAgeDetails = findViewById(R.id.textViewAgeDetails);
        textViewColorDetails = findViewById(R.id.textViewColorDetails);
        textViewIsAdoptedDetails = findViewById(R.id.textViewIsAdoptedDetails);
        TextView textview_title = findViewById(R.id.textview_title);
        FloatingActionButton fabLogout = findViewById(R.id.fab_logout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Provide feedback to user
                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Navigate to login screen
                Intent intent = new Intent(getApplicationContext(), PetsLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        contact_my = findViewById(R.id.contact_my);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String selectedPetName = sharedPreferences.getString("selectedPetName", "");
        int userId = sharedPreferences.getInt("userId", -1);


            if (userId != -1 && !selectedPetName.isEmpty()) {

                Pets pet = petsDataBase.getPetByUserIdAndName(userId, selectedPetName);

                    // Populate views with pet data
                    textViewNameDetails.setText(pet.getName());
                    textViewTypeDetails.setText(pet.getType());
                    textViewAgeDetails.setText("Age: " + pet.getAge() + " years");
                    textViewColorDetails.setText("Color: " + pet.getColor());
                    textViewIsAdoptedDetails.setText("Yes");
                    textview_title.setText("Hello The" + pet.getType() + " " + pet.getName());
                    // Load pet image using Glide
                    Glide.with(this)
                            .load(pet.getPicUrl())
                            .placeholder(R.drawable.baseline_person_24)
                            .error(R.drawable.baseline_pets_24)
                            .into(imageViewPetDetails);

                    contact_my.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String contactPhone = pet.getContactPhone();
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + contactPhone));
                            startActivity(intent);
                        }
                    });


            }

    }
}