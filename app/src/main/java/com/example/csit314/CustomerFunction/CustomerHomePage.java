package com.example.csit314.CustomerFunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.csit314.Adapter.RequestAdapter;
import com.example.csit314.EntityClass.Request;
import com.example.csit314.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        bottomNavFunction();
    }


    private void bottomNavFunction()
    {
        BottomNavigationView nav = findViewById(R.id.customer_nav);
        nav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homelogo:
                {
                    Intent myIntent = new Intent(CustomerHomePage.this, CustomerHomePage.class);
                    CustomerHomePage.this.finish();
                    CustomerHomePage.this.startActivity(myIntent);
                    break;
                }
                case R.id.requestlogo:
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.home_page_fragment_container, CustomerRequest.class, null)
                            .addToBackStack("name")
                            .commit();;
                    break;
                }
//                case R.id.savedlogo:
//                {
//                    Intent myIntent = new Intent(UserPassword.this, SavedPage.class);
//                    UserPassword.this.finish();
//                    UserPassword.this.startActivity(myIntent);
//                    break;
//                }
//                case R.id.toolbarlogo:
//                {
//                    Intent myIntent = new Intent(UserPassword.this, ToolbarPage.class);
//                    UserPassword.this.finish();
//                    UserPassword.this.startActivity(myIntent);
//                    break;
//                }
            }
            return true;
        });
    }

}