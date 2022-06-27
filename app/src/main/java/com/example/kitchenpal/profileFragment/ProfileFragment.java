package com.example.kitchenpal.profileFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kitchenpal.Login;
import com.example.kitchenpal.R;
import com.example.kitchenpal.objects.User;
import com.example.kitchenpal.adapters.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewpager;
    TextView profileUsername;
    String username;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        addFragment(view);
        profileUsername = view.findViewById(R.id.profileUsername);
        Button logoutBtn = view.findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("users").child(mAuth.getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("username").getValue(String.class);
                profileUsername.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    protected String getUsername() {
        return this.username;
    }

    private void addFragment(View view) {
        tabLayout = view.findViewById(R.id.profileTabLayout);
        viewpager = view.findViewById(R.id.viewPager);

        ViewpagerAdapter vpa = new ViewpagerAdapter(getChildFragmentManager(),getLifecycle());
        vpa.addFragments(new ProfileFavouritesFragment(), " Favourites");
        vpa.addFragments(new ProfileMyRecipesFragment(),"My Recipes");
        viewpager.setAdapter(vpa);
        new TabLayoutMediator(tabLayout, viewpager, (tab, pos) -> tab.setText(vpa.getTitles(pos))).attach();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
    }
}

