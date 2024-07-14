package com.example.androidsession.presentation.main_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.data_service.TeamDS;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.TeamEntity;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    TextView test;
    EditText editTextName, editTextAge, editTextUsername, editTextPassword, editTextSalary, editTextIsActive;
    Spinner spinnerCity, spinnerTeam;
    Button buttonRegister, buttonLogin;
//    Button button;

    MainActivityViewModel viewModel;
    CityDS cityDS;
    TeamDS teamDS;
    LoginDS loginDS;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        cityDS = new CityDS(this);
        teamDS = new TeamDS(this);
        loginDS = new LoginDS(this);

        viewInit();
//        textObserveFunction();

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
        try {
            List<CityEntity> cities = cityDS.getCityUIDAndName();
            ArrayAdapter<CityEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCity.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinnerTeam = findViewById(R.id.spinnerTeam);
        try {
            List<TeamEntity> teams = teamDS.getTeamUIDAndName();
            ArrayAdapter<TeamEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teams);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTeam.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> {
            buttonFunctions();
//            test.setText(getResources().getString(R.string.test2));
        });

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonFunctions(){
        double salary = Double.parseDouble(editTextSalary.getText().toString());
        int cityUid = ((CityEntity) spinnerCity.getSelectedItem()).getUID();
        int teamUid = ((TeamEntity) spinnerTeam.getSelectedItem()).getUID();
        viewModel.login(this, editTextUsername.getText().toString(), editTextPassword.getText().toString(), editTextIsActive.length());
        int loginUid = loginDS.lastInsertedId;
//        int loginUid = uid+1;
        viewModel.register(this, editTextName.getText().toString(), editTextAge.length(), cityUid, teamUid, salary, loginUid);
    }

//    private void textObserveFunction() {
//        viewModel.getText().observe(this, this::setText);
//    }

//    private void setText(String value){
//        test.setText(value);
//    }
}