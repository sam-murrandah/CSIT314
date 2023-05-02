package com.example.csit314.MainLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csit314.CustomerFunction.CustomerHomePage;
import com.example.csit314.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homePageButton();
        userRegisterButton();
    }

    private void homePageButton()
    {
        Button button = findViewById(R.id.hp_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomerHomePage.class));
                finish();
            }
        });
    }
    private void userRegisterButton()
    {
        Button button = findViewById(R.id.hp_button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
            }
        });
    }
}