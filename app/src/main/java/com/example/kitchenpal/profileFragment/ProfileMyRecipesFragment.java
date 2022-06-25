package com.example.kitchenpal.profileFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.ProfileMyRecipesAdapter;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ProfileMyRecipesFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ProfileMyRecipesFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment profileMyRecipes.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileMyRecipesFragment newInstance(String param1, String param2) {
//        ProfileMyRecipesFragment fragment = new ProfileMyRecipesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    RecyclerView recyclerView;
    List<RecipesViewerModel> recipesCardList;
    ProfileMyRecipesAdapter profileMyRecipesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_my_recipes, container, false);

        recyclerView = root.findViewById(R.id.profile_my_recipe_rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipesCardList = new ArrayList<>();
        recipesCardList.add(new RecipesViewerModel(R.drawable.pizza, "mine 1", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.pizza, "mine 2", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.pizza, "mine 3", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.burger, "mine 4", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.fries, "mine 5", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.burger, "mine 6", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.fries, "mine 7", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.pizza, "mine 8", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.pizza, "mine 9", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.burger, "mine 10", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.burger, "mine 11", "me"));
        recipesCardList.add(new RecipesViewerModel(R.drawable.fries, "END", "me"));

        profileMyRecipesAdapter = new ProfileMyRecipesAdapter(getActivity(), recipesCardList);
        recyclerView.setAdapter(profileMyRecipesAdapter);
        // Inflate the layout for this fragment
        return root;
    }
}