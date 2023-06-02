package com.example.csit314.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.csit314.EntityClass.Request;
import com.example.csit314.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<Request> requests;
    private Context context;
    private IClickItemRequest iClickItemRequest;

    public interface IClickItemRequest{
        void showRequest(String requestId);
    }

    public RequestAdapter (List <Request> requests, Context context, IClickItemRequest iClickItemRequest)
    {
        this.requests = requests;
        this.context = context;
        this.iClickItemRequest = iClickItemRequest;
    }

    public void setRequestsList (List<Request> requestList)
    {
        this.requests = requestList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View requestView = inflater.inflate(R.layout.customer_request_object, parent, false);

        RequestViewHolder viewHolder = new RequestViewHolder(requestView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        final Request request = requests.get(position);
        if (request == null)
            return;

        holder.title.setText(request.getTitle());
        holder.location.setText("Location: "+request.getLocation());
        holder.hashTag.setText("Type: "+request.getHashTag());
        holder.salary.setText("Salary: "+ request.getSalary());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView title;
        private TextView location;
        private TextView hashTag;
        private TextView salary;

        public RequestViewHolder (View itemView)
        {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.ccr_textview17);
            location = itemView.findViewById((R.id.ccr_textview18));
            hashTag = itemView.findViewById(R.id.ccr_textview20);
            salary = itemView.findViewById(R.id.ccr_textview15);

        }

        public void bind (final String requestId, final RequestAdapter.IClickItemRequest iClickItemRequest)
        {
            itemView.setOnClickListener (new View.OnClickListener(){
                @Override
                public void onClick (View v)
                {
                    iClickItemRequest.showRequest(requestId);
                }
            });
        }
    }

}
