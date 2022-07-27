package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.models.RecipesViewerModel;
import com.example.kitchenpal.objects.PantryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PantryItemsAdapter extends RecyclerView.Adapter<PantryItemsAdapter.ViewHolder> {

    Context context;
    List<PantryItemsModel> modelList;

    public PantryItemsAdapter(Context context, List<PantryItemsModel> list) {
        this.context = context;
        this.modelList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pantry_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelList.get(position).getItemName();
        String publisher = modelList.get(position).getPublisher();
        boolean isRequested = modelList.get(position).isRequested();

        holder.image.setImageResource(modelList.get(position).getImage());
        holder.publisher.setText(publisher);
        holder.itemName.setText(name);
        holder.condition.setText(modelList.get(position).getCondition());
        if (isRequested) {
            holder.request.setVisibility(View.GONE);
        } else {
            holder.request.setVisibility(View.VISIBLE);
        }

        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String requesterName = snapshot.child("username").getValue(String.class);

                                Toast.makeText(context, requesterName, Toast.LENGTH_SHORT).show();

                                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference()
                                        .child("pantry_items_sort_by_item_name")
                                        .child(name);
                                itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        PantryItem pantryItem = snapshot.getValue(PantryItem.class);

                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            String getUsername = dataSnapshot.child("username").getValue(String.class);
                                                            String getUID = dataSnapshot.getKey();

                                                            if(getUsername.equals(publisher)) {
                                                                FirebaseDatabase.getInstance().getReference()
                                                                        .child("users")
                                                                        .child(getUID)
                                                                        .child("request_from_others")
                                                                        .child(requesterName)
                                                                        .child(name)
                                                                        .setValue(pantryItem);

                                                                FirebaseDatabase.getInstance().getReference()
                                                                        .child("users")
                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                        .child("pending")
                                                                        .child(name)
                                                                        .setValue(pantryItem);
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

    public void updateData(List<PantryItemsModel> itemsModelList) {
        this.modelList = itemsModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName, publisher, condition, request;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.pantry_image);
            itemName = v.findViewById(R.id.item_name_pantry);
            publisher = v.findViewById(R.id.publisher_pantry);
            condition = v.findViewById(R.id.condition_pantry);
            request = v.findViewById(R.id.request_button);
        }
    }

    public void setFilteredList(List<PantryItemsModel> filteredList) {
        this.modelList= filteredList;
        notifyDataSetChanged();
    }
}
