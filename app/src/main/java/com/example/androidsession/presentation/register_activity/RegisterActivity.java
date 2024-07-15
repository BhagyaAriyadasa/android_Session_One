package com.example.androidsession.presentation.register_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.TeamEntity;
import com.example.androidsession.presentation.login_activity.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextAge, editTextUsername, editTextPassword, editTextSalary, editTextIsActive;
    Spinner spinnerCity, spinnerTeam;
    Button buttonRegister, buttonLogin;

    RegisterActivityViewModel viewModel;
    LoginDS loginDS;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);
        loginDS = new LoginDS(this);

        viewInit();
        observeData();
        viewModel.fetchCities(this);
        viewModel.fetchTeams(this);
    }

    private void viewInit(){
        //initializing view
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextSalary = findViewById(R.id.editTextSalary);
        editTextIsActive = findViewById(R.id.editTextIsActive);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerTeam = findViewById(R.id.spinnerTeam);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> buttonFunctions());

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void observeData() {
        viewModel.getCities().observe(this, cities -> {
            ArrayAdapter<CityEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCity.setAdapter(adapter);
        });

        viewModel.getTeams().observe(this, teams -> {
            ArrayAdapter<TeamEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teams);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTeam.setAdapter(adapter);
        });
    }

    private void buttonFunctions(){
        double salary = Double.parseDouble(editTextSalary.getText().toString());
        int cityUid = ((CityEntity) spinnerCity.getSelectedItem()).getUID();
        int teamUid = ((TeamEntity) spinnerTeam.getSelectedItem()).getUID();
        viewModel.postLogin(this, editTextUsername.getText().toString(), editTextPassword.getText().toString(), editTextIsActive.length());
        int loginUid = loginDS.lastInsertedId;
        viewModel.postRegister(this, editTextName.getText().toString(), editTextAge.length(), cityUid, teamUid, salary, loginUid);
    }
}