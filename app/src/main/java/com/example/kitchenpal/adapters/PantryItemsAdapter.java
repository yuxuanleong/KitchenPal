package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.models.RecipesViewerModel;

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
        holder.image.setImageResource(modelList.get(position).getImage());
        holder.publisher.setText(modelList.get(position).getPublisher());
        holder.itemName.setText(modelList.get(position).getItemName());
        holder.condition.setText(modelList.get(position).getCondition());

        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Button chatButton;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.pantry_image);
            itemName = v.findViewById(R.id.item_name_pantry);
            publisher = v.findViewById(R.id.publisher_pantry);
            condition = v.findViewById(R.id.condition_pantry);
            chatButton = v.findViewById(R.id.chat_button);
        }
    }

    public void setFilteredList(List<PantryItemsModel> filteredList) {
        this.modelList= filteredList;
        notifyDataSetChanged();
    }
}
