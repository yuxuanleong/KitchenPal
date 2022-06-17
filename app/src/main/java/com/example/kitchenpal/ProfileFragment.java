package com.example.kitchenpal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kitchenpal.adapters.VPA;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewpager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        addFragment(view);
        return view;
    }

    private void addFragment(View view) {
        tabLayout = view.findViewById(R.id.profileTabLayout);
        viewpager = view.findViewById(R.id.viewPager);

        VPA vpa = new VPA(getChildFragmentManager(),getLifecycle());
        vpa.addFragments(new ProfileFavouritesFragment(), " Favourites");
        vpa.addFragments(new profileMyRecipes(),"My Recipes");
        viewpager.setAdapter(vpa);
        new TabLayoutMediator(tabLayout, viewpager, (tab, pos) -> tab.setText(vpa.getTitles(pos))).attach();
    }
}

