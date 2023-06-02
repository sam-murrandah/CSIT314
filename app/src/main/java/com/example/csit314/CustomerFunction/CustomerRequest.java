package com.example.csit314.CustomerFunction;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.csit314.R;


public class CustomerRequest extends Fragment {

    private static final String TAG = "CustomerRequest";
    private String userId, accountType;
    private static final String MyPreferences = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_request, container, false);

        readSharedPref(view);
        checkLoginStatus(view);
        newRequestBut(view);
        return view;
    }

    private void checkLoginStatus(View view)
    {
        if (userId.equals("") || userId == null)
        {
            Toast.makeText(view.getContext(), "Please login to active this function", Toast.LENGTH_SHORT).show();
        }
    }

    private void newRequestBut(View view)
    {
        Button button = view.findViewById(R.id.cr_button);
        if (accountType.equals("professional") || userId.equals(""))
        {
            button.setVisibility(View.INVISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerNewRequest.class, null)
                        .addToBackStack("name")
                        .commit();;
            }
        });
    }

    private void readSharedPref(View view)
    {
        SharedPreferences sh = view.getContext().getSharedPreferences(MyPreferences, MODE_PRIVATE);
        userId = sh.getString("userId","");
        accountType = sh.getString("accountType", "");
    }
}