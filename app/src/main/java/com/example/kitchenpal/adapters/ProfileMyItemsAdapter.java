package com.example.kitchenpal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

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
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.confirm_del);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                dialog.getWindow().setLayout(1050, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                AppCompatButton del = dialog.findViewById(R.id.del_dialog_del);
                AppCompatButton cancel = dialog.findViewById(R.id.del_dialog_cancel);
                TextView itemName = dialog.findViewById(R.id.things_to_be_del);

                itemName.setText(name + "?");

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteItem(name);
                        dialog.dismiss();
                        Toast.makeText(context, name + " successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();


            }
        });

    }

    private void deleteItem(String itemName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("/pantry_items_sort_by_item_name").child(itemName).removeValue();

        ref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()) {

                    if (dss.hasChild(itemName)) {
                        dss.child(itemName).getRef().removeValue();
                    }

                    for(DataSnapshot dss1 : dss.getChildren()) {

                        if (dss1.hasChild(itemName)) {
                            dss1.child(itemName).getRef().removeValue();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
