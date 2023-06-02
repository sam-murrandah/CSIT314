package com.example.csit314.CustomerFunction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerJobByType#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerJobByType extends Fragment {

    private static final String TAG = "CustomerJobByType";
    private int jobType;

    private RequestAdapter requestAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Request> requests;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_customer_job_by_type, container, false);

        getJobType();
        setTitle(view);
        showRecycleView(view);
        backButton(view);
        loadRequestListByNewest();
        return view;
    }

    private void loadRequestListByNewest(){
        String type = "";
        if (jobType == 0)
            type = "House Cleaning";
        else if (jobType == 1 )
            type = "Tree Removal";
        else if (jobType == 2 )
            type = "Roof Cleaning";
        else if (jobType == 3 )
            type = "Fence  Installs";
        else if (jobType == 4 )
            type = "Oven Repairs";
        requests = new ArrayList<>();
        db.collection("Requests").whereEqualTo("type_of_request", 0).orderBy("salary", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Request request = new Request();
                        request.setTitle(document.getString("title"));
                        request.setLocation(document.getString("location"));
                        request.setHashTag((document.getDouble("type_of_request").intValue()));
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

    private void backButton (View v)
    {
        Button button = v.findViewById(R.id.jbt_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.home_page_fragment_container, CustomerHomePageFragment.class, null)
                        .addToBackStack("name")
                        .commit();;
            }
        });
    }

    private void showRecycleView(View v)
    {
        requests = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.jbt_request_list);
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

    private void sendRequestToRequestDetail (final String request)
    {
        Bundle bundle = new Bundle();
        bundle.putString("requestId", request);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.home_page_fragment_container, CustomerRequestDetail.class, bundle)
                .addToBackStack("name")
                .commit();;
    }

    private void getJobType ()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            jobType = bundle.getInt("type");
        }
        Log.d(TAG, "Job Type By Int: "+jobType);
    }

    private void setTitle(View v) {
        TextView textView = v.findViewById(R.id.jbt_title);
        if (jobType == 0) {
            textView.setText("House Cleaning");
        }
        else if (jobType == 1 ) {
            textView.setText("Tree Removal");
        }
        else if (jobType == 2 ) {
            textView.setText("Roof Cleaning");
        }
        else if (jobType == 3 ) {
            textView.setText("Fence  Installs");
        }
        else if (jobType == 4 ) {
            textView.setText("Oven Repairs");
        }
        else {
            textView.setText("Error");
        }
    }

}