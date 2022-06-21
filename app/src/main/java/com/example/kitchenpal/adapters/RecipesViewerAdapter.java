package com.example.kitchenpal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.List;

public class RecipesViewerAdapter extends RecyclerView.Adapter<RecipesViewerAdapter.ViewHolder>{
    Context context;
    List<RecipesViewerModel> listModels;

    public RecipesViewerAdapter(Context context, List<RecipesViewerModel> list) {
        this.context = context;
        this.listModels = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipes_recipeview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(listModels.get(position).getImage());
        holder.recipeName.setText(listModels.get(position).getRecipeName());
        holder.createdBy.setText(listModels.get(position).getPublisher());
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView recipeName, createdBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
            createdBy = itemView.findViewById(R.id.createdBy);
        }
    }
}
