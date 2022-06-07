package com.example.kitchenpal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kitchenpal.adapters.VPA;
import com.google.android.material.tabs.TabLayout;

public class Profile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private VPA vpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        FragmentManager fm = getSupportFragmentManager();
        vpa = new VPA(fm, getLifecycle());
        viewPager.setAdapter(vpa);
    }
}