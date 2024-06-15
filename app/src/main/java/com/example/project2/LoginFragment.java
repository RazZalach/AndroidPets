package com.example.project2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    DbDiary dbDiary;
    private EditText emailLogin, passLogin;
    private EditText usernameRegister, emailRegister, passRegister;
    private LinearLayout loginSection, registerSection;
    private Button switchButton, switchButtonRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        dbDiary = new DbDiary(requireContext());

        // Initialize views
        emailLogin = view.findViewById(R.id.email_login);
        passLogin = view.findViewById(R.id.pass_login);
        usernameRegister = view.findViewById(R.id.username_register);
        emailRegister = view.findViewById(R.id.email_register);
        passRegister = view.findViewById(R.id.pass_register);
        loginSection = view.findViewById(R.id.login_section);
        registerSection = view.findViewById(R.id.register_section);
        switchButton = view.findViewById(R.id.switchbtn);
        switchButtonRegister = view.findViewById(R.id.switchbtn_register);

        Button loginButton = view.findViewById(R.id.lgin_btn);
        Button registerButton = view.findViewById(R.id.register_btn);

        // Set click listeners
        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> registerUser());
        switchButton.setOnClickListener(v -> switchSections());
        switchButtonRegister.setOnClickListener(v -> switchSections());

        return view;
    }

    private void loginUser() {
        String email = emailLogin.getText().toString().trim();
        String password = passLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (dbDiary.loginUser(user)) {
            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
            // Navigate to another fragment or activity
        } else {
            Toast.makeText(getContext(), "Login failed. Please check your email and password", Toast.LENGTH_SHORT).show();
        }
    }


    private void registerUser() {
        String username = usernameRegister.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String password = passRegister.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        if (dbDiary.addUser(user)) {
            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            // Optionally, switch back to the login section
            switchSections();
        } else {
            Toast.makeText(getContext(), "Registration failed. Email may already be in use", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchSections() {
        if (loginSection.getVisibility() == View.VISIBLE) {
            loginSection.setVisibility(View.GONE);
            registerSection.setVisibility(View.VISIBLE);
        } else {
            loginSection.setVisibility(View.VISIBLE);
            registerSection.setVisibility(View.GONE);
        }
    }
}
