package com.example.kitchenpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {

    private RecyclerView recipesViewer;
    private List<RecipesViewerModel> recipesViewerModelList;
    private SearchView searchView;
    private RecipesViewerAdapter recipesViewerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recipes, container, false);

        Button text = (Button) root.findViewById(R.id.textMode);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeText.class);
                startActivity(intent);
            }
        });

        searchView = root.findViewById(R.id.search_bar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

            private void filterList(String newText) {
                List<RecipesViewerModel> filteredList = new ArrayList<RecipesViewerModel>();
                for (RecipesViewerModel recipe :  recipesViewerModelList) {
                    if (recipe.getRecipeName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(recipe);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    recipesViewerAdapter.setFilteredList(filteredList);
                }
            }
        });

        recipesViewer = root.findViewById(R.id.recipeView);

        ////vertical recyclerView

        recipesViewerModelList = new ArrayList<>();
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.burger, "Burger", "baker101"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.fries, "French fries", "cook101"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.pizza, "Mac N Cheese", "cook123"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.pizza, "Pizza", "PizzaMan"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.burger, "Cheeseburger", "hehexd"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.fries, "Shoestring fries", "Mcdonalds"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.pizza, "Margherita pizza", "LINO's"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.burger, "Mac N Cheese", "cook123"));
        recipesViewerModelList.add(new RecipesViewerModel(R.drawable.fries, "Mac N Cheese", "cook123"));

        recipesViewerAdapter = new RecipesViewerAdapter(getActivity(), recipesViewerModelList);
        recipesViewer.setAdapter(recipesViewerAdapter);
        recipesViewer.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        recipesViewer.setHasFixedSize(true);

        return root;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}