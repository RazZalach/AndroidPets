package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class PetsRegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextUserName, editTextPassword;
    private Button buttonRegister, buttonAlreadyRegistered;

    private PetsDataBase petsDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonAlreadyRegistered = findViewById(R.id.buttonAlreadyRegistered);

        petsDataBase = new PetsDataBase(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String userName = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PetsRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(PetsRegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(email, userName, password);
                    petsDataBase.registerUser(user);
                    Toast.makeText(PetsRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PetsRegisterActivity.this, PetsLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        buttonAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetsRegisterActivity.this, PetsLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
