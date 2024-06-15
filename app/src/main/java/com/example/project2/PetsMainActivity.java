package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class PetsMainActivity extends AppCompatActivity {
    private TextView textWelcome;
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_main);
        textWelcome = findViewById(R.id.text_welcome);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default value -1 if not found
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language is not supported or missing data.");
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
            }
        });

        FloatingActionButton fabLogout = findViewById(R.id.fab_logout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Provide feedback to user
                Toast.makeText(PetsMainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Navigate to login screen
                Intent intent = new Intent(PetsMainActivity.this, PetsLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        PetsDataBase petsDataBase = new PetsDataBase(this);
        User user = petsDataBase.getUserById(userId);

        // Display user's name in the welcome TextView
        if (user != null) {
            String welcomeMessage = "Welcome to Pets App, " + user.getUserName() + "!";
            textWelcome.setText(welcomeMessage);
            textToSpeech.speak(welcomeMessage, TextToSpeech.QUEUE_FLUSH, null, null);

        } else {
            textWelcome.setText("Welcome to Pets App!"); // Default message if user not found
        }
        BottomNavigationView navigation = findViewById(R.id.navigation_pets);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.navigation_my){
                    loadNavFragment(new MyPetsFragment());
                }
                else if (id==R.id.navigation_all) {
                    loadNavFragment(new PetsApiListFragment());
                }   else if (id==R.id.navigation_add_pet) {
                    loadNavFragment(new AddPetToApiFragment());
                }
                return true;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_my);

    }
    private void  loadNavFragment(Fragment fragment){
        //This funcation for load fragment directly

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //use replace
        fragmentTransaction.replace(R.id.container_pets, fragment);
        fragmentTransaction.commit();
    }
}