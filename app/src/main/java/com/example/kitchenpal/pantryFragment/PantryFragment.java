package com.example.kitchenpal.pantryFragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.PantryItemsAdapter;
import com.example.kitchenpal.models.PantryItemsModel;
import com.example.kitchenpal.objects.PantryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PantryFragment extends Fragment {

    private SearchView searchView;

    private RecyclerView itemsViewer;
    private List<PantryItemsModel> itemsModelList = new ArrayList<>();
    private PantryItemsAdapter pantryItemsAdapter;
    private FloatingActionButton fab;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button uploadBtn;
    String contact, username;

    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pantry, container, false);

        itemsViewer = root.findViewById(R.id.pantry_items);
        fab = root.findViewById(R.id.fab);
        fab.setImageTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.upload_item_popup))
                        .setExpanded(true, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .create();

                View v = dialogPlus.getHolderView();
                EditText etContact = v.findViewById(R.id.contact_popup);

                FirebaseHelper.getCurrUsernameData(new FirebaseSuccessListener() {
                    @Override
                    public void onDataFound(boolean isDataFetched) {

                        if(isDataFetched){

                            username = FirebaseHelper.getCurrUsername();

                            FirebaseHelper.getContactData(username, new FirebaseSuccessListener() {
                                @Override
                                public void onDataFound(boolean isDataFetched) {

                                    if (isDataFetched) {
                                        etContact.setVisibility(View.GONE);
                                    } else {
                                        etContact.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                });

                dialogPlus.show();

                radioGroup = v.findViewById(R.id.radioGroup);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = v.findViewById(radioId);
                uploadBtn = v.findViewById(R.id.upload_pantry_item);
                EditText etItemName = v.findViewById(R.id.itemName_popup);

                uploadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String itemName = etItemName.getText().toString();
                        String condition = String.valueOf(radioButton.getText());

                        if(etContact.getVisibility() == View.VISIBLE) {
                            String stContact = etContact.getText().toString();
                            FirebaseDatabase.getInstance().getReference()
                                    .child("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("contact")
                                    .setValue(stContact);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("users_sort_by_username")
                                    .child(username)
                                    .child("contact")
                                    .setValue(stContact);
                        }

                        if (!itemName.isEmpty()) {
                            PantryItem item = new PantryItem(itemName, condition, username);

                            addPantryItemsToDatabase(item);

                            Toast.makeText(getContext(), "Pantry Item uploaded", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Please enter item name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

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
                pantryItemsAdapter.setFilteredList(filteredList);
            }
        });
        pantryItemsAdapter = new PantryItemsAdapter(getActivity(), itemsModelList);
        itemsViewer.setAdapter(pantryItemsAdapter);

        getPantryItemsFromDatabase();

//        itemsModelList.add(new PantryItemsModel(R.drawable.pizza, "burger", "elyse", "Opened", true));
//        itemsModelList.add(new PantryItemsModel(R.drawable.pizza, "fries", "elyse2", "Brand new", false));

        itemsViewer.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        itemsViewer.setHasFixedSize(true);

        return root;
    }

    private void addPantryItemsToDatabase(PantryItem item) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("my_pantry_items")
                .child(item.getName())
                .setValue(item);

        FirebaseDatabase.getInstance().getReference()
                .child("pantry_items_sort_by_item_name")
                .child(item.getName())
                .setValue(item);
    }

    private void getPantryItemsFromDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("pantry_items_sort_by_item_name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    itemsModelList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        PantryItem pantryItem = dss.getValue(PantryItem.class);
                        checkIfRequested(pantryItem, new FirebaseSuccessListener() {
                            @Override
                            public void onDataFound(boolean isDataFetched) {
                                if (isDataFetched) {
                                    pantryItemsAdapter = new PantryItemsAdapter(getActivity(), itemsModelList);
                                    itemsViewer.setAdapter(pantryItemsAdapter);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkIfRequested(PantryItem pantryItem, FirebaseSuccessListener dataFetched) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("pending")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(pantryItem.getName())) {
                            itemsModelList.add(new PantryItemsModel(R.drawable.pizza, pantryItem.getName(), pantryItem.getPublisher(), pantryItem.getCondition(), true));
                        } else {
                            itemsModelList.add(new PantryItemsModel(R.drawable.pizza, pantryItem.getName(), pantryItem.getPublisher(), pantryItem.getCondition(), false));
                        }
                        dataFetched.onDataFound(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}