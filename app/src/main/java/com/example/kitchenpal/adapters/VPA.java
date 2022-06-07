package com.example.kitchenpal.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kitchenpal.ProfileFavouritesFragment;
import com.example.kitchenpal.ProfileMyRecipesFragment;

public class VPA extends FragmentStateAdapter {

    public VPA(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProfileFavouritesFragment();
            case 1:
                return new ProfileMyRecipesFragment();
            default:
                return new ProfileFavouritesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
