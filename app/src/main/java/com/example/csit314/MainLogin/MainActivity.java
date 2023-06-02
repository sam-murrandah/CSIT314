package com.example.csit314.MainLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.csit314.CustomerFunction.CustomerHomePage;
import com.example.csit314.CustomerFunction.CustomerNewRequest;
import com.example.csit314.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String MyPreferences = "MyPrefs";
    private String userId, accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        readSharedPref();
        homePageButton();
        userRegisterButton();
        Log.d(TAG, "sdfad: "+userId);
    }

    private void readSharedPref()
    {
//        SharedPreferences preferences = getSharedPreferences(MyPreferences, 0);
//        preferences.edit().clear().commit();

        SharedPreferences sh = getSharedPreferences(MyPreferences, MODE_PRIVATE);
        userId = sh.getString("userId","");
        sharePrefs(userId);
        sh = getSharedPreferences(MyPreferences, MODE_PRIVATE);
        accountType = sh.getString("accountType", "");
    }
    public void sharePrefs (String id)
    {
        // if userid is not null -> share the userid to other functions
        if (!userId.equals(""))
        {
            FirebaseFirestore.getInstance().collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists())
                        {
                            String userType = document.getString("type");

                            SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId",id);
                            editor.putString("accountType", userType);
                            editor.commit();
                        }
                    }
                    else
                    {
                        Log.d(TAG, "Error: "+task.getException().toString());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Error: "+e.getMessage());
                }
            });
        }
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
                startActivity(new Intent(MainActivity.this, MainLogin.class));
                finish();
            }
        });
    }
}