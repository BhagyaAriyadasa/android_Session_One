package com.example.androidsession.presentation.register_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.Utils;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.TeamEntity;
import com.example.androidsession.presentation.login_activity.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextAge, editTextUsername, editTextPassword, editTextSalary;
    Spinner spinnerCity, spinnerTeam;
    Button buttonRegister, buttonLogin;
    CheckBox checkIsActive;

    RegisterActivityViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);

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
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerTeam = findViewById(R.id.spinnerTeam);
        checkIsActive = findViewById(R.id.checkboxIsActive);

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
        boolean isActive;
        String password = "";

        if(checkIsActive.isChecked()){
            isActive=true;
        }else{
            isActive=false;
        }

//        if(viewModel.isValidPassword(password)){
//            password = editTextPassword.getText().toString();
//            System.out.println(password);
//        }else{
//            Toast.makeText(RegisterActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
//        }

        if(editTextPassword.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty() || editTextUsername.getText().toString().isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please fill required fields", Toast.LENGTH_SHORT).show();
        }else if(viewModel.isValidPassword(editTextPassword.getText().toString())){
            password = editTextPassword.getText().toString();
            double salary = editTextSalary.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(editTextSalary.getText().toString());
            int cityUid = ((CityEntity) spinnerCity.getSelectedItem()).getUID();
            int teamUid = ((TeamEntity) spinnerTeam.getSelectedItem()).getUID();
            viewModel.postLogin(this, editTextUsername.getText().toString(), password, isActive);
            int loginUid = Utils.lastInsertedLoginUId;
            viewModel.postRegister(this, editTextName.getText().toString(), editTextAge.length(), cityUid, teamUid, salary, loginUid);
            clearFields();
            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        }else if(!viewModel.isValidPassword(editTextPassword.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
        }else{
            clearFields();
        }
    }

    private void clearFields() {
        editTextName.setText("");
        editTextAge.setText("");
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextSalary.setText("");
        spinnerCity.setSelection(0);
        spinnerTeam.setSelection(0);
        checkIsActive.setChecked(false);
    }
}