package com.example.kitchenpal.requestFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class RequestFragment extends Fragment {

    TabLayout tablayout;
    ViewPager2 viewpager2;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_request, container, false);

        addFragment(root);

        return root;
    }

    private void addFragment(View root) {
        tablayout = root.findViewById(R.id.requestsTabLayout);
        viewpager2 = root.findViewById(R.id.requestsViewPager);

        ViewpagerAdapter vpa = new ViewpagerAdapter(getChildFragmentManager(), getLifecycle());
        vpa.addFragments(new RequestsOthersFragment(), "From Others");
        vpa.addFragments(new RequestsPendingFragment(), "Pending");
        vpa.addFragments(new RequestsAcceptedFragment(), "Accepted");

        viewpager2.setAdapter(vpa);
        new TabLayoutMediator(tablayout, viewpager2, (tab, pos) -> tab.setText(vpa.getTitles(pos))).attach();
    }

}