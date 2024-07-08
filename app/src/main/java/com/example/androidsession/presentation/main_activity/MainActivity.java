package com.example.androidsession.presentation.main_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidsession.R;
import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.table_entity.CityEntity;

public class MainActivity extends AppCompatActivity {

    TextView test;
    Button button;

    MainActivityViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewInit();
        textObserveFunction();

    }

    private void viewInit(){
        //initializing view
        test = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            buttonFunctions();
//            test.setText(getResources().getString(R.string.test2));
        });
    }

    private void buttonFunctions(){
        viewModel.clickMeMethod(this);
    }

    private void textObserveFunction() {
        viewModel.getText().observe(this, this::setText);
    }

    private void setText(String value){
        test.setText(value);
    }
}