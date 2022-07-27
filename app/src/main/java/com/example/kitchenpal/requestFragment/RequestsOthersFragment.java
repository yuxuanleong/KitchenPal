package com.example.kitchenpal.requestFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.ProfileMyRecipesAdapter;
import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.adapters.RequestFromOthersAdapter;
import com.example.kitchenpal.models.ProfileMyRecipeModel;
import com.example.kitchenpal.models.RecipesViewerModel;
import com.example.kitchenpal.models.RequestFromOthersModel;
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

public class RequestsOthersFragment extends Fragment {

    public RequestsOthersFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private RequestFromOthersAdapter adapter;
    private List<RequestFromOthersModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_requests_accept, container, false);

        recyclerView = root.findViewById(R.id.requests_accept);

        getRequestFromOthersFromDatabase();

        return root;
    }

    private void getRequestFromOthersFromDatabase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("request_from_others");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list.clear();
                        for (DataSnapshot dss : snapshot.getChildren()) {
                            String requester = dss.getKey();
                            for (DataSnapshot dss2 : dss.getChildren()) {
                                PantryItem recipe = dss2.getValue(PantryItem.class);
                                assert recipe != null;
                                list.add(new RequestFromOthersModel(R.drawable.pizza, recipe.getName(), requester, recipe.getCondition()));
                            }
                        }
                    }

                    adapter = new RequestFromOthersAdapter(getActivity(), list);
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