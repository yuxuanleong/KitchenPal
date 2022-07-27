package com.example.kitchenpal.profileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.ProfileMyRecipesAdapter;
import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.models.ProfileMyRecipeModel;
import com.example.kitchenpal.models.RecipesViewerModel;
import com.example.kitchenpal.objects.Recipe;
import com.example.kitchenpal.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileMyRecipesFragment extends Fragment {

    public ProfileMyRecipesFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<ProfileMyRecipeModel> profileCardList = new ArrayList<>();
    private ProfileMyRecipesAdapter recipesViewerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_my_recipes, container, false);

        getRecipesFromDatabase();

        recyclerView = root.findViewById(R.id.profile_my_recipe_rec);

        return root;
    }

    private void getRecipesFromDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("my_recipes");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    profileCardList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Recipe recipe = dss.getValue(Recipe.class);
                        assert recipe != null;
                        profileCardList.add(new ProfileMyRecipeModel(R.drawable.pizza, recipe.getName(), recipe.getPublisher()));
                    }
                }
                recipesViewerAdapter = new ProfileMyRecipesAdapter(getActivity(), profileCardList);
                recyclerView.setAdapter(recipesViewerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}