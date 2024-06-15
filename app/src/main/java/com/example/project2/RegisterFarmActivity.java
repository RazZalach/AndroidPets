package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterFarmActivity extends AppCompatActivity {
    private EditText editTextName, editTextPassword, editTextEmail, editTextJob, editTextPhone;
    private Button buttonRegister;
    private DbFarm dbFarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextJob = findViewById(R.id.editTextJob);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonRegister = findViewById(R.id.buttonRegister);

        dbFarm = new DbFarm(getApplicationContext());

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmployee();
            }
        });
    }
    private void registerEmployee() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String job = editTextJob.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || job.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long employeeId = dbFarm.registerEmployee(name, password, email, job, phone);
        if (employeeId > 0) {
            Toast.makeText(this, "Employee registered successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginFarmActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }


}