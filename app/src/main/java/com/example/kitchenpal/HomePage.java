package com.example.kitchenpal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kitchenpal.profileFragment.ProfileFragment;
import com.example.kitchenpal.recipesFragment.RecipesFragment;
import com.example.kitchenpal.pantryFragment.PantryFragment;
import com.example.kitchenpal.requestFragment.RequestFragment;
import com.example.kitchenpal.uploadFragment.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        replaceFragment(new RecipesFragment());

        BottomNavigationView navigationBar = findViewById(R.id.navigationBar);
        navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile_nav:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.pantry_nav:
                    replaceFragment(new PantryFragment());
                    break;
                case R.id.upload_nav:
                    replaceFragment(new UploadFragment());
                    break;
                case R.id.request_nav:
                    replaceFragment(new RequestFragment());
                    break;
                case R.id.recipes_nav:
                default:
                    replaceFragment(new RecipesFragment());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
}