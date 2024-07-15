package com.example.androidsession.presentation.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.presentation.home_page_activity.HomePageActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    Button loginButton;
    LoginActivityViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        loginButton.setOnClickListener(view -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            viewModel.login(LoginActivity.this,username,password);
        });

        observeData();
    }

    private void observeData() {
        viewModel.getLoginResult().observe(this, isActive -> {
            if (isActive) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Inactive User", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
