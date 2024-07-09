package com.example.androidsession.presentation.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidsession.R;
import com.example.androidsession.database.data_service.LoginDS;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    Button loginButton;
    LoginDS loginDS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginDS = new LoginDS(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                try {
                    if (loginDS.getIsActiveval(username, password)) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
