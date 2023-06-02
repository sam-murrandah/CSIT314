package com.example.csit314.CustomerFunction;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.csit314.EntityClass.Request;
import com.example.csit314.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomerNewRequest extends Fragment {

    private static final String MyPreferences = "MyPrefs";

    private static final String TAG = "CustomerNewRequest";

    private int requestTypeByInt = -1;

    private String userId, accountType;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String KEY_TITLE = "title";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_TYPE_OF_REQUEST = "type_of_request";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SALARY = "salary";
    private static final String KEY_ESTIMATE_HOUR = "hour_estimated";
    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_STATUS = "status";


    private EditText tTitle, tLocation, tDescription, tSalary, tEstimatedHours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_customer_new_request, container, false);

        readSharedPref(view);

        tTitle = (EditText) view.findViewById(R.id.cnw_textinput);
        tLocation = (EditText) view.findViewById(R.id.cnw_textinput4);
        tDescription = (EditText) view.findViewById(R.id.cnw_textinput1);
        tSalary = (EditText) view.findViewById(R.id.cnw_textinput2);
        tEstimatedHours = (EditText) view.findViewById(R.id.cnw_textinput3);

        addRequestType(view);
        backButton(view);
        confirmButton(view);

        return view;

    }

    private void readSharedPref(View view)
    {
        SharedPreferences sh = view.getContext().getSharedPreferences(MyPreferences, MODE_PRIVATE);
        userId = sh.getString("userId","");
        accountType = sh.getString("accountType", "");
    }

    private void confirmButton(View v)
    {
        Button button = v.findViewById(R.id.cnw_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mTitle = tTitle.getText().toString() != null ? tTitle.getText().toString() : null;
                String mtLocation = tLocation.getText().toString() != null ? tLocation.getText().toString() : null;
                String mDescription = tDescription.getText().toString() != null ? tDescription.getText().toString() : null;
                String mSalary = tSalary.getText().toString() != null ? tSalary.getText().toString() : null;
                String mEstimatedHours = tEstimatedHours.getText().toString() != null ? tEstimatedHours.getText().toString() : null;

                if (TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mtLocation) || TextUtils.isEmpty(mDescription) || TextUtils.isEmpty(mSalary) || TextUtils.isEmpty(mEstimatedHours))
                {
                    Toast.makeText(getActivity(), "Empty credentials", Toast.LENGTH_SHORT).show();
                }
                else if (requestTypeByInt == -1)
                {
                    Toast.makeText(getActivity(), "Please select type for your request", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Request request = new Request();
                    request.setTitle(mTitle);
                    request.setLocation(mtLocation);
                    request.setDescription(mDescription);
                    request.setSalary(Double.parseDouble(mSalary));
                    request.setEstimatedHours(Integer.parseInt(mEstimatedHours));
                    request.setStatus(0);
                    request.setCustomerId(userId);

                    String type = requestTypeByString();
                    request.setHashTag(requestTypeByInt);

                    uploadData(v, request);
                }
            }
        });
    }

    private void uploadData(View v, Request request)
    {
        String requestId = db.collection("Requests").document().getId();

        Map<String, Object> newRequest = new HashMap<>();
        newRequest.put(KEY_TITLE, request.getTitle());
        newRequest.put(KEY_LOCATION, request.getLocation());
        newRequest.put(KEY_TYPE_OF_REQUEST, request.getHashTag());
        newRequest.put(KEY_DESCRIPTION, request.getDescription());
        newRequest.put(KEY_SALARY, request.getSalary());
        newRequest.put(KEY_ESTIMATE_HOUR, request.getEstimatedHours());
        newRequest.put(KEY_CUSTOMER_ID, request.getCustomerId());
        newRequest.put(KEY_STATUS, request.getStatus());

        db.collection("Requests").document(requestId).set(newRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "New request added", Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.home_page_fragment_container, CustomerRequest.class, null)
                            .addToBackStack("name")
                            .commit();;
                }
                else
                {
                    Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backButton(View v)
    {
        Button button = v.findViewById(R.id.cnw_logo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getParentFragmentManager().beginTransaction();
                trans.replace(R.id.home_page_fragment_container, CustomerRequest.class, null)
                        .addToBackStack("name")
                        .commit();;
            }
        });
    }

    private String requestTypeByString()
    {
        String type = "";
        if (requestTypeByInt == 0)
        {
            type = "House Cleaning";
        }
        else if (requestTypeByInt == 1)
        {
            type = "Tree Removal";
        }
        else if (requestTypeByInt == 2)
        {
            type = "Roof Cleaning";
        }
        else if (requestTypeByInt == 3)
        {
            type = "Fence Install";
        }
        else if (requestTypeByInt == 4)
        {
            type = "Oven Repair";
        }
        else
        {
            return null;
        }
        return type;
    }

    private void addRequestType(View v)
    {
        String[] userTypes = {"House Cleaning", "Tree Removal", "Roof Cleaning", "Fence Install", "Oven Repair"};
        Spinner spinner = (Spinner) v.findViewById(R.id.cnw_spinner);
//        TextView textView = (TextView) findViewById(R.id.user_register_logo5);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestTypeByInt = spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                requestTypeByInt = spinner.getSelectedItemPosition();
            }
        });
//        Log.d("userTypeByInt", String.valueOf(userTypeByInt));
    }



}