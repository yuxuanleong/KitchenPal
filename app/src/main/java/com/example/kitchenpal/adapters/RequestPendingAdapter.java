package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.models.RequestPendingModel;
import com.example.kitchenpal.objects.PantryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestPendingAdapter extends RecyclerView.Adapter<RequestPendingAdapter.ViewHolder> {

    Context context;
    List<RequestPendingModel> modelList;

    public RequestPendingAdapter(Context context, List<RequestPendingModel> list) {
        this.context = context;
        this.modelList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request_pending_accpeted, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelList.get(position).getItemName();
        String publisher = modelList.get(position).getPublisher();


        holder.image.setImageResource(modelList.get(position).getImage());
        holder.publisher.setText(publisher);
        holder.itemName.setText(name);
        holder.condition.setText(modelList.get(position).getCondition());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName, publisher, condition;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.request_pending_accepted_image);
            itemName = v.findViewById(R.id.item_name_request_pending_accepted);
            publisher = v.findViewById(R.id.publisher_request_pending_accepted);
            condition = v.findViewById(R.id.condition_request_pending_accepted);
        }
    }
}
