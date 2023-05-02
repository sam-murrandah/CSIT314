package com.example.csit314.MainLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csit314.CustomerFunction.CustomerHomePage;
import com.example.csit314.CustomerFunction.CustomerNewRequest;
import com.example.csit314.R;

public class MainLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        backToHomePageButton();
        createNewAccountButton();
    }

    private void backToHomePageButton()
    {
        Button button = findViewById(R.id.ml_button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, CustomerHomePage.class));
                finish();
            }
        });
    }


    private void createNewAccountButton()
    {
        Button button = findViewById(R.id.ml_button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, Register.class));
                finish();
            }
        });
    }
}