package com.example.kitchenpal.profileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class ProfileFavouritesFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ProfileFavouritesFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFavouritesFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileFavouritesFragment newInstance(String param1, String param2) {
//        ProfileFavouritesFragment fragment = new ProfileFavouritesFragment();
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
    List<RecipesViewerModel> profileCardList;
    RecipesViewerAdapter recipesViewerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_favourites, container, false);

        recyclerView = root.findViewById(R.id.profile_fav_rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        profileCardList = new ArrayList<>();
        profileCardList.add((new RecipesViewerModel(R.drawable.burger, "Mushroom Burger", "shakeshac")));
        profileCardList.add((new RecipesViewerModel(R.drawable.fries, "Shoestring Fries", "randomcafe")));
        profileCardList.add((new RecipesViewerModel(R.drawable.pizza, "Burrata Pizza", "LINO's")));


        RecipesViewerAdapter recipesViewerAdapter= new RecipesViewerAdapter(getActivity(), profileCardList);
        recyclerView.setAdapter(recipesViewerAdapter);
        // Inflate the layout for this fragment
        return root;
    }
}