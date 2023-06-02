package com.example.csit314.MainLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.csit314.CustomerFunction.CustomerHomePage;
import com.example.csit314.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String TAG = "Register";
    private static final String MyPreferences = "MyPrefs";

    private int userTypeByInt = -1;

    private static final String KEY_NAME = "full_name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ACCOUNT_TYPE = "type";
    private static final String KEY_MEMBERSHIP = "membership_status";
    private static final String KEY_REQUEST_LIST = "request_ids";
    private static final String KEY_TOTAL_REQUEST= "total_request_done";
    private static final String KEY_AVG_STAR = "rating";

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        passwordButton();
        addUserType();
        finishButton();
        backButton();
    }

    private void finishButton()
    {
        Button button = (Button) findViewById(R.id.us_button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser()
    {

        EditText fullNameEt = (EditText) findViewById(R.id.us_textinput);
        EditText usernameEt = (EditText) findViewById(R.id.us_textinput1);
        EditText passwordEt = (EditText) findViewById(R.id.us_textinput2);

        String fullName = fullNameEt.getText().toString();
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();


        if(TextUtils.isEmpty(fullName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        {
            Toast.makeText(Register.this, "Empty credentials", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6)
        {
            Toast.makeText(Register.this, "Password is too short", Toast.LENGTH_SHORT).show();
        }
        else if (userTypeByInt == -1)
        {
            Toast.makeText(Register.this, "Please select purpose for your account", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            createAuthWithPhone(username, password, fullName, userType);
            createAuthWithEmail(username, password, fullName);
        }

    }
    private void createAuthWithEmail(String username, String password, String fullName)
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                 if (userTypeByInt == 0)
                 {
                     List<String> requestsList = new ArrayList<>();
                     Map<String, Object> customer = new HashMap<>();
                     customer.put(KEY_NAME, fullName);
                     customer.put(KEY_USERNAME, username);
                     customer.put(KEY_PASSWORD, password);
                     customer.put(KEY_MEMBERSHIP, 0);
                     customer.put(KEY_ACCOUNT_TYPE, "customer");
                     customer.put(KEY_REQUEST_LIST, requestsList);

                     String id = db.collection("Users").document().getId();
                     db.collection("Users").document(id).set(customer)
                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     sharePrefs(id, "customer");
                                     Toast.makeText(Register.this, "Customer saved", Toast.LENGTH_SHORT).show();
                                     Log.d(TAG, "Customer saved");
                                     Intent intent = new Intent(Register.this, CustomerHomePage.class);
                                     Register.this.finish();
                                     Register.this.startActivity(intent);
                                 }
                             })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(Register.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                     Log.d(TAG, e.getMessage());
                                     Intent intent = new Intent(Register.this, Register.class);
                                     Register.this.finish();
                                     Register.this.startActivity(intent);
                                 }
                             });
                 }
                 else if (userTypeByInt == 1)
                 {
                     List<String> requestsList = new ArrayList<>();
                     Map<String, Object> professional = new HashMap<>();
                     professional.put(KEY_NAME, fullName);
                     professional.put(KEY_USERNAME, username);
                     professional.put(KEY_PASSWORD, password);
                     professional.put(KEY_ACCOUNT_TYPE, "professional");
                     professional.put(KEY_REQUEST_LIST, requestsList);
                     professional.put(KEY_TOTAL_REQUEST,0);
                     professional.put(KEY_AVG_STAR,0);

                     String id = db.collection("Users").document().getId();
                     db.collection("Users").document(id).set(professional)
                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     sharePrefs(id, "professional");
                                     Toast.makeText(Register.this, "Professional saved", Toast.LENGTH_SHORT).show();
                                     Log.d(TAG, "Professional saved");
                                     Intent intent = new Intent(Register.this, CustomerHomePage.class);
                                     Register.this.finish();
                                     Register.this.startActivity(intent);
                                 }
                             })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(Register.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                     Log.d(TAG, e.getMessage());
                                     Intent intent = new Intent(Register.this, Register.class);
                                     Register.this.finish();
                                     Register.this.startActivity(intent);
                                 }
                             });
                 }
                 else
                 {
                     Toast.makeText(Register.this, "Please choose your purpose!!", Toast.LENGTH_SHORT).show();
                 }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UserRegister.this, "Email or Phone number is not valid.", Toast.LENGTH_SHORT).show();
                Toast.makeText(Register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sharePrefs (String userId, String userType)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPreferences, MODE_PRIVATE);
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

    private void addUserType()
    {
        String[] userTypes = {"Customer","Professional"};
        Spinner spinner = (Spinner) findViewById(R.id.us_spinner);
//        TextView textView = (TextView) findViewById(R.id.user_register_logo5);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userTypeByInt = spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userTypeByInt = spinner.getSelectedItemPosition();
            }
        });
//        Log.d("userTypeByInt", String.valueOf(userTypeByInt));
    }

    private void passwordButton()
    {
        EditText password = (EditText) findViewById(R.id.us_textinput2);
        Button hideButton = (Button) findViewById(R.id.us_button2);
        Button showButton = (Button) findViewById(R.id.us_button3);

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

    private void backButton()
    {
        Button button = findViewById(R.id.us_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, MainLogin.class));
                finish();
            }
        });
    }
}