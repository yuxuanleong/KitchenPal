package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.RequestPendingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProfileMyItemsAdapter extends RecyclerView.Adapter<ProfileMyItemsAdapter.ViewHolder> {

    Context context;
    List<RequestPendingModel> modelList;

    public ProfileMyItemsAdapter(Context context, List<RequestPendingModel> list) {
        this.context = context;
        this.modelList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_profile_my_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelList.get(position).getItemName();
        String publisher = modelList.get(position).getPublisher();

        holder.image.setImageResource(modelList.get(position).getImage());
        holder.publisher.setText(publisher);
        holder.itemName.setText(name);
        holder.condition.setText(modelList.get(position).getCondition());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("/pantry_items_sort_by_item_name").child(name).removeValue();

                ref.child("users").child(FirebaseHelper.getCurrUserID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dss : snapshot.getChildren()) {

                            if (dss.hasChild(name)) {
                                dss.child(name).getRef().removeValue();
                            }

                            for(DataSnapshot dss1 : dss.getChildren()) {

                                if (dss1.hasChild(name)) {
                                    dss1.child(name).getRef().removeValue();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
        TextView itemName, publisher, condition;
        AppCompatButton delete;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.request_pending_accepted_image);
            itemName = v.findViewById(R.id.item_name_request_pending_accepted);
            publisher = v.findViewById(R.id.publisher_request_pending_accepted);
            condition = v.findViewById(R.id.condition_request_pending_accepted);
            delete = v.findViewById(R.id.del_button);
        }
    }
}
