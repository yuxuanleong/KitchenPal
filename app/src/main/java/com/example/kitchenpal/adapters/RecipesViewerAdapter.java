package com.example.kitchenpal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.RecipesViewerModel;
import com.example.kitchenpal.objects.Recipe;
import com.example.kitchenpal.recipesFragment.RecipeText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipesViewerAdapter extends RecyclerView.Adapter<RecipesViewerAdapter.ViewHolder> implements Filterable {
    Context context;
    private List<RecipesViewerModel> listModels;
    private List<RecipesViewerModel> listModelsFull;

    public RecipesViewerAdapter(Context context, List<RecipesViewerModel> list) {
        this.context = context;
        this.listModels = list;
        listModelsFull = new ArrayList<>(listModels);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = listModels.get(position).getRecipeName();
        String publisher = listModels.get(position).getPublisher();
        int image  = listModels.get(position).getImage();
        boolean isFavourites = listModels.get(position).isFavourite();

        //set the relevant information
        holder.image.setImageResource(image);
        holder.recipeName.setText(name);
        holder.createdBy.setText(publisher);
        holder.likeButton.setChecked(isFavourites);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, listModels.get(position).getRecipeName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RecipeText.class);
                intent.putExtra("name", name);
                intent.putExtra("publisher", publisher);
                intent.putExtra("image", image);
                context.startActivity(intent);
            }
        });

        holder.likeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference()
                            .child("recipes_sort_by_recipe_name")
                            .child(name);

                    recipeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Recipe recipe = snapshot.getValue(Recipe.class);
                            if (recipe != null) {
                                DatabaseReference favouritesRef = FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("favourites")
                                        .child(recipe.getName());
                                favouritesRef.setValue(new Recipe(recipe.getName(), recipe.getPublisher(), recipe.getIngredientArrayList(), recipe.getStepArrayList()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("favourites")
                            .child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    listModels.remove(listModels.get(position));
                                    notifyDataSetChanged();
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView recipeName, createdBy;
        ToggleButton likeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
            createdBy = itemView.findViewById(R.id.createdBy);
            likeButton = (ToggleButton) itemView.findViewById(R.id.likeButton);
        }
    }

    @Override
    public Filter getFilter() {
        return recipesFilter;
    }

    private Filter recipesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RecipesViewerModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(listModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RecipesViewerModel model : listModelsFull) {
                    if (model.getRecipeName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(model);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listModels.clear();
            listModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setFilteredList(List<RecipesViewerModel> filteredList) {
        this.listModels= filteredList;
        notifyDataSetChanged();
    }
}
