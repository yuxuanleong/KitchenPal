package com.example.kitchenpal.pantryFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.PantryItemsAdapter;
import com.example.kitchenpal.adapters.RecipesViewerAdapter;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.models.RecipesViewerModel;

import java.util.ArrayList;
import java.util.List;

public class PantryFragment extends Fragment {

    private SearchView searchView;

    private RecyclerView itemsViewer;
    private List<PantryItemsModel> itemsModelList = new ArrayList<>();
    private PantryItemsAdapter pantryItemsAdapter;

    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pantry, container, false);

        itemsViewer = root.findViewById(R.id.pantry_items);

        //search function
        searchView = root.findViewById(R.id.search_bar_pantry);
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
                List<PantryItemsModel> filteredList = new ArrayList<>();
                for (PantryItemsModel item : itemsModelList) {
                    if (item.getItemName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    pantryItemsAdapter.setFilteredList(filteredList);
                }
            }
        });

        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "french flour", "user10","Brand New"));
        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "baking powder", "user9","Opened"));
        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "chocolate", "user8","Opened"));
        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "flour", "user7","Brand New"));
        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "yeast", "user6","Brand New"));
        itemsModelList.add(new PantryItemsModel(R.drawable.fries, "End", "user5","Opened"));


        pantryItemsAdapter = new PantryItemsAdapter(getActivity(), itemsModelList);
        itemsViewer.setAdapter(pantryItemsAdapter);
        itemsViewer.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        itemsViewer.setHasFixedSize(true);

        return root;
    }
}