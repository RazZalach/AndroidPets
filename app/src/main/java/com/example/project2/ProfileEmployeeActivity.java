package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class ProfileEmployeeActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView textViewCountdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employee);

        // Retrieve employee data from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long employeeId =Long.parseLong(sharedPreferences.getString("employee_id", ""));
        String employeeName = sharedPreferences.getString("employee_name", "");

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle the error
                    }
                } else {
                    // Initialization failed
                }
            }
        });
        if(employeeId != -1){
            TextView textViewEmployeeName = findViewById(R.id.textViewEmployeeName);
            textViewEmployeeName.setText("Welcome, " + employeeName);

            String welcomeMessage = "Welcome, " + employeeName;
            textToSpeech.speak(welcomeMessage, TextToSpeech.QUEUE_FLUSH, null, null);
        }
//
        textViewCountdown = findViewById(R.id.textViewCountdown);

        // Initialize CountDownTimer
        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewCountdown.setText("Remaining: " + millisUntilFinished / 1000 + " Second To Add Farm!");
            }

            @Override
            public void onFinish() {
                textViewCountdown.setText("To Slow!!!!");

            }
        }.start();




        BottomNavigationView navigation = findViewById(R.id.navigation_profile);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id= item.getItemId();
                if (id==R.id.navigation_add_farm){
                    loadNavFragment(new AddFarmFragment());
                }
                 else if (id==R.id.navigation_my_farms) {
                    loadNavFragment(new AllFarmsEmployeeFragment());
                }



                return true;
            }
        });
        navigation.setSelectedItemId(R.id.navigation_add_farm);
    }
    private void  loadNavFragment(Fragment fragment){
        //This funcation for load fragment directly

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //use replace
        fragmentTransaction.replace(R.id.container_profile, fragment);
        fragmentTransaction.commit();
    }
}