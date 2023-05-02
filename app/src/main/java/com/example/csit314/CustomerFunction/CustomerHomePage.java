package com.example.csit314.CustomerFunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.csit314.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerHomePage extends AppCompatActivity {

    private RecyclerView recyclerView;

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
                case R.id.maplogo:
                {
                    ConstraintLayout constraintLayout = findViewById(R.id.customer_fragement);
                    constraintLayout.removeAllViews();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.customer_fragement, CustomerNewRequest.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();
                }
//                case R.id.eventlogo:
//                {
//                    Intent myIntent = new Intent(UserPassword.this, EventPage.class);
//                    UserPassword.this.finish();
//                    UserPassword.this.startActivity(myIntent);
//                    break;
//                }
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