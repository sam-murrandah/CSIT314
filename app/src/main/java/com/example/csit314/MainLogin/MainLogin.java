package com.example.csit314.MainLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csit314.CustomerFunction.CustomerHomePage;
import com.example.csit314.CustomerFunction.CustomerNewRequest;
import com.example.csit314.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainLogin extends AppCompatActivity {

    private static final String MyPreferences = "MyPrefs";

    private static final String TAG = "MainLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        passwordButton();
        loginButton();
        backToHomePageButton();
        createNewAccountButton();
    }

    private void loginButton()
    {
        TextInputEditText inputUsername = findViewById(R.id.ml_textinput);
        TextInputEditText inputPassword = findViewById(R.id.ml_textinput1);

        Button signInBut = (Button) findViewById(R.id.ml_button2);
        signInBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsernameTxt = inputUsername.getText().toString().trim();
                String inputPasswordTxt = inputPassword.getText().toString().trim();
//                sharePrefs(inputUsernameTxt);
//                Intent myIntent = new Intent (getActivity(), ToolbarPage.class);
//                getActivity().finish();
//                getActivity().startActivity(myIntent);
                if (inputUsernameTxt.isEmpty() || inputPasswordTxt.isEmpty())
                {
                    Toast.makeText(MainLogin.this, "Username or Password is incorrect. Please try again", Toast.LENGTH_SHORT).show();
                    Intent refresh = new Intent (MainLogin.this, MainLogin.class);
                    MainLogin.this.finish();
                    MainLogin.this.startActivity(refresh);
                }
                else if (inputPasswordTxt.length() < 6)
                {
                    Toast.makeText(MainLogin.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(inputUsernameTxt, inputPasswordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                getUserInformation(inputUsernameTxt);
                                Toast.makeText(MainLogin.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getContext(),MainLogin.class));
                            }
                        }
                    });
                }
            }
        });
    }

    private void getUserInformation(String username)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").whereEqualTo("username", username).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document: queryDocumentSnapshots)
                {
                    String userId = document.getId();
                    String type = document.getString("type");
                    sharePrefs(userId, type);
                    startActivity(new Intent(MainLogin.this, CustomerHomePage.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainLogin.this, "No data found in Database", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sharePrefs (String userId, String userType)
    {
        SharedPreferences sharedPreferences = MainLogin.this.getSharedPreferences(MyPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId",userId);
        editor.putString("accountType", userType);
        //User
//        editor.putString("userId","Yi4jGdP4A3rIWWvBBVnX");
//        editor.putString("accountType", "user");

        //Admin
//        editor.putString("userId","XdPlEFotCA23tFIJjIIM");
//        editor.putString("accountType", "admin");
        editor.commit();
        Log.d(TAG, "share prefs: "+userId+" "+userType);
    }

    private void passwordButton()
    {
        EditText password = (EditText) findViewById(R.id.ml_textinput1);
        Button hideButton = (Button) findViewById(R.id.ml_button1);
        Button showButton = (Button) findViewById(R.id.ml_button);

        showButton.setVisibility(View.INVISIBLE);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setTransformationMethod(null);
                showButton.setVisibility(View.VISIBLE);
                hideButton.setVisibility(View.INVISIBLE);
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setTransformationMethod(new PasswordTransformationMethod());
                showButton.setVisibility(View.INVISIBLE);
                hideButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void backToHomePageButton()
    {
        Button button = findViewById(R.id.ml_button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, MainActivity.class));
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