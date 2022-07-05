package com.example.kitchenpal.recipesFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.ArrayList;
import java.util.List;

import com.example.kitchenpal.R;
import com.example.kitchenpal.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecipesFragment extends Fragment {
    private SearchView searchView;

    private RecyclerView recipesViewer;
    private List<RecipesViewerModel> recipesViewerModelList = new ArrayList<>();
    private RecipesViewerAdapter recipesViewerAdapter;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_recipes, container, false);

        //initialise recycler view
        recipesViewer = root.findViewById(R.id.recipeView);

        //search function
        searchView = root.findViewById(R.id.search_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

            private void filterList(String newText) {
                List<RecipesViewerModel> filteredList = new ArrayList<RecipesViewerModel>();
                for (RecipesViewerModel recipe :  recipesViewerModelList) {
                    if (recipe.getRecipeName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(recipe);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    recipesViewerAdapter.setFilteredList(filteredList);
                }
            }
        });

        getRecipesFromDatabase();

        //set an empty adapter
        recipesViewerAdapter = new RecipesViewerAdapter(getActivity(), recipesViewerModelList);
        recipesViewer.setAdapter(recipesViewerAdapter);

        recipesViewer.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        recipesViewer.setHasFixedSize(true);

        return root;
    }

    private void getRecipesFromDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("recipes_sort_by_recipe_name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    recipesViewerModelList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Recipe recipe = dss.getValue(Recipe.class);
                        checkIfFavourites(recipe, new FirebaseSuccessListener() {
                            @Override  
                            public void onDataFound(boolean isDataFetched) {
                                if (isDataFetched) {

                                    //update adapter after data is fetched
                                    recipesViewerAdapter = new RecipesViewerAdapter(getActivity(), recipesViewerModelList);
                                    recipesViewer.setAdapter(recipesViewerAdapter);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkIfFavourites(Recipe recipe, FirebaseSuccessListener dataFetched) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(recipe.getName())) {
                    recipesViewerModelList.add(new RecipesViewerModel(R.drawable.pizza, recipe.getName(), recipe.getPublisher(), true));
                } else {
                    recipesViewerModelList.add(new RecipesViewerModel(R.drawable.pizza, recipe.getName(), recipe.getPublisher(), false));
                }
                dataFetched.onDataFound(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}