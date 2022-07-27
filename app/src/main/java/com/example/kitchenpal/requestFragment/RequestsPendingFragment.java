package com.example.kitchenpal.requestFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.RequestFromOthersAdapter;
import com.example.kitchenpal.adapters.RequestPendingAdapter;
import com.example.kitchenpal.models.RequestFromOthersModel;
import com.example.kitchenpal.models.RequestPendingModel;
import com.example.kitchenpal.objects.PantryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RequestsPendingFragment extends Fragment {

    public RequestsPendingFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<RequestPendingModel> list = new ArrayList<>();
    private RequestPendingAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_requests_pending, container, false);

        recyclerView = root.findViewById(R.id.requests_pending);
        getRequestPendingFromDatabase();

        return root;
    }

    private void getRequestPendingFromDatabase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("pending");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dss2 : snapshot.getChildren()) {
                        PantryItem recipe = dss2.getValue(PantryItem.class);
                        assert recipe != null;
                        list.add(new RequestPendingModel(R.drawable.fries, recipe.getName(), recipe.getPublisher(), recipe.getCondition()));
                    }
                }

                adapter = new RequestPendingAdapter(getActivity(), list);
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