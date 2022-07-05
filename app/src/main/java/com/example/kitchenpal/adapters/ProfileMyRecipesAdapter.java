package com.example.kitchenpal.adapters;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.RecipeText;
import com.example.kitchenpal.models.ProfileMyRecipeModel;

import java.util.List;

public class ProfileMyRecipesAdapter extends RecyclerView.Adapter<ProfileMyRecipesAdapter.ViewHolder> {

    Context context;
    List<ProfileMyRecipeModel> list;

    public ProfileMyRecipesAdapter(Context context, List<ProfileMyRecipeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProfileMyRecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileMyRecipesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_profile_my_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMyRecipesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = list.get(position).getName();
        Integer image = list.get(position).getImage();
        String publisher = list.get(position).getPublisher();

        holder.imageView.setImageResource(image);
        holder.name.setText(name);
        holder.publisher.setText(publisher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeText.class);
                intent.putExtra("name", name);
                intent.putExtra("publisher", publisher);
                intent.putExtra("image", image);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView publisher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipeImage_myrecipe);
            name = itemView.findViewById(R.id.recipeName_myrecipe);
            publisher = itemView.findViewById(R.id.createdBy_myrecipe);
        }
    }
}
