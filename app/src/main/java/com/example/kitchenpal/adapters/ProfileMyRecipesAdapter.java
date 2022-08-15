package com.example.kitchenpal.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.ProfileMyRecipeModel;
import com.example.kitchenpal.recipesFragment.RecipeText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                        deleteItem(name, publisher, position);
                        dialog.dismiss();
                        Toast.makeText(context, name + " successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });
    }

    private void deleteItem(String name, String publisher, int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("recipes_sort_by_recipe_name").child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });

        ref.child("recipes_sort_by_username").child(publisher).child(name).removeValue();

        ref.child("users").child(FirebaseHelper.getCurrUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()) {

                    if (dss.hasChild(name)) {
                        dss.child(name).getRef().removeValue();
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
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView publisher;
        AppCompatButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipeImage_myrecipe);
            name = itemView.findViewById(R.id.recipeName_myrecipe);
            publisher = itemView.findViewById(R.id.createdBy_myrecipe);
            delete = itemView.findViewById(R.id.del_recipe_button);
        }
    }
}
