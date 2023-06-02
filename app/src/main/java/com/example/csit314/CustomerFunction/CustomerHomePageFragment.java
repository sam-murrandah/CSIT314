package com.example.csit314.CustomerFunction;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.csit314.Adapter.RequestAdapter;
import com.example.csit314.EntityClass.Request;
import com.example.csit314.R;
import com.example.csit314.SupportClass.SpacingItemDecorator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomePageFragment extends Fragment {


    private RecyclerView recyclerView;

    private String userId, accountType;
    private static final String MyPreferences = "MyPrefs";
    private static final String TAG = "CustomerHomePage";

    private RequestAdapter requestAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Request> requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home_page, container, false);

        readSharedPref(view);
        showRecycleView(view);
        loadRequestListByNewest();
        jobByTypeButton(view);

        return view;
    }

    private void jobByTypeButton(View v)
    {
        ImageButton houseButton = v.findViewById(R.id.chp_button);
        ImageButton treeButton = v.findViewById(R.id.chp_button1);
        ImageButton roofButton = v.findViewById(R.id.chp_button2);
        ImageButton fenceButton = v.findViewById(R.id.chp_button3);
        ImageButton ovenButton = v.findViewById(R.id.chp_button4);

        houseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerJobByType.class, bundle)
                        .addToBackStack("name")
                        .commit();;
            }
        });
        treeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerJobByType.class, bundle)
                        .addToBackStack("name")
                        .commit();;
            }
        });
        roofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerJobByType.class, bundle)
                        .addToBackStack("name")
                        .commit();;
            }
        });
        fenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 3);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerJobByType.class, bundle)
                        .addToBackStack("name")
                        .commit();;
            }
        });
        ovenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 4);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerJobByType.class, bundle)
                        .addToBackStack("name")
                        .commit();;
            }
        });
    }

    private void showRecycleView(View v)
    {
        requests = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.chp_request_list);
        requestAdapter = new RequestAdapter(requests, getActivity(), new RequestAdapter.IClickItemRequest(){
            @Override
            public void showRequest (String requestId) {sendRequestToRequestDetail (requestId);}
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(requestAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void loadRequestListByNewest(){
        requests = new ArrayList<>();
        db.collection("Requests").orderBy("salary", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Request request = new Request();
                        request.setTitle(document.getString("title"));
                        request.setLocation(document.getString("location"));
                        request.setHashTag(document.getDouble("type_of_request").intValue());
                        request.setSalary(document.getDouble("salary"));

                        requests.add(request);
                    }
                    requestAdapter.setRequestsList(requests);
                }
                else
                {
                    Toast.makeText((getActivity()), "No data found in Database", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void sendRequestToRequestDetail (final String request)
    {
        Bundle bundle = new Bundle();
        bundle.putString("requestId", request);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.home_page_fragment_container, CustomerRequestDetail.class, bundle)
                .addToBackStack("name")
                .commit();;
    }

    private void readSharedPref(View v)
    {
        SharedPreferences sh = v.getContext().getSharedPreferences(MyPreferences, MODE_PRIVATE);
        userId = sh.getString("userId","");
        accountType = sh.getString("accountType", "");
    }

}