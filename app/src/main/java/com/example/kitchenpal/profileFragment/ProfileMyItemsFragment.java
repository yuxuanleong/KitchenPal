package com.example.kitchenpal.profileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.ProfileMyItemsAdapter;
import com.example.kitchenpal.adapters.ProfileMyRecipesAdapter;
import com.example.kitchenpal.models.ProfileMyRecipeModel;
import com.example.kitchenpal.models.RequestPendingModel;
import com.example.kitchenpal.objects.PantryItem;
import com.example.kitchenpal.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileMyItemsFragment extends Fragment {


    public ProfileMyItemsFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<RequestPendingModel> list = new ArrayList<>();
    private ProfileMyItemsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_my_items, container, false);

        recyclerView = v.findViewById(R.id.profile_my_items_rec);

        getItemsFromDatabase();

        return v;
    }

    private void getItemsFromDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("my_pantry_items");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        PantryItem recipe = dss.getValue(PantryItem.class);
                        assert recipe != null;
                        list.add(new RequestPendingModel(R.drawable.pizza, recipe.getName(), recipe.getPublisher(), recipe.getCondition()));
                    }
                }
                adapter = new ProfileMyItemsAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
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