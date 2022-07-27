package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.models.RequestFromOthersModel;
import com.example.kitchenpal.objects.PantryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestFromOthersAdapter extends RecyclerView.Adapter<RequestFromOthersAdapter.ViewHolder> {

    Context context;
    List<RequestFromOthersModel> modelList;
    String requesterUID;
    String currUsername;

    public RequestFromOthersAdapter(Context context, List<RequestFromOthersModel> list) {
        this.context = context;
        this.modelList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request_accept, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelList.get(position).getItemName();
        String requester = modelList.get(position).getRequester();
        Integer image = modelList.get(position).getImage();
        String condition = modelList.get(position).getCondition();

        holder.image.setImageResource(image);
        holder.requester.setText(requester);
        holder.itemName.setText(name);
        holder.condition.setText(condition);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("request_from_others")
                        .child(requester)
                        .child(name)
                        .removeValue();

                findRequesterUID(requester, new FirebaseSuccessListener() {
                    @Override
                    public void onDataFound(boolean isDataFetched) {
                        if (isDataFetched) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("users")
                                    .child(requesterUID)
                                    .child("pending")
                                    .child(name)
                                    .removeValue();
                            findCurrentUsername(new FirebaseSuccessListener() {
                                @Override
                                public void onDataFound(boolean isDataFetched) {
                                    PantryItem pantryitem = new PantryItem(name, condition, currUsername);
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("users")
                                            .child(requesterUID)
                                            .child("accepted")
                                            .child(name)
                                            .setValue(pantryitem);
                                }
                            });

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName, requester, condition;
        Button accept;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.requestaccept_image);
            itemName = v.findViewById(R.id.item_name_requestaccept);
            requester = v.findViewById(R.id.requester);
            condition = v.findViewById(R.id.condition_requestaccept);
            accept = v.findViewById(R.id.accept_button);
        }
    }

    public void setFilteredList(List<RequestFromOthersModel> filteredList) {
        this.modelList= filteredList;
        notifyDataSetChanged();
    }

    private void findCurrentUsername(FirebaseSuccessListener isDataFetched) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currUsername = snapshot.child("username").getValue(String.class);
                        isDataFetched.onDataFound(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void findRequesterUID(String requester, FirebaseSuccessListener isDataFetched) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dss: snapshot.getChildren()) {
                            String getUsername = dss.child("username").getValue(String.class);
                            if(getUsername.equals(requester)) {
                                requesterUID = dss.getKey();
                                isDataFetched.onDataFound(true);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
