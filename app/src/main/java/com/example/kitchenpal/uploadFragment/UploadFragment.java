package com.example.kitchenpal.uploadFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kitchenpal.R;
import com.example.kitchenpal.objects.Recipe;
import com.example.kitchenpal.profileFragment.ProfileMyRecipesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UploadFragment extends Fragment {

    public UploadFragment() {
        // Required empty public constructor
    }

    EditText etRecipeName;
    String recipeName, publisher;
    Button buttonSubmit;
    FirebaseAuth mAuth;
    FirebaseDatabase myDatabase;
    DatabaseReference users_user_ref, recipes_sort_by_username_ref, recipes_sort_by_name_ref;
    AddStepFragment stepFrag = new AddStepFragment();
    AddIngredientFragment ingreFrag = new AddIngredientFragment();
    ProfileMyRecipesFragment myRecipesFragment = new ProfileMyRecipesFragment();
    ArrayList<String> ingreList, stepList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);

        etRecipeName = root.findViewById(R.id.etRecipeName);
        buttonSubmit = root.findViewById(R.id.btnSubmit);
//        ingreFrag = new AddIngredientFragment();
//        stepFrag = new AddStepFragment();
        myDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        users_user_ref = myDatabase.getReference("users").child(mAuth.getCurrentUser()
                .getUid());
        recipes_sort_by_username_ref = myDatabase.getReference("recipes_sort_by_username");
        recipes_sort_by_name_ref = myDatabase.getReference().child("recipes_sort_by_recipe_name");

        //get publisher
        users_user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                publisher = snapshot.child("username").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        replaceFragment(ingreFrag);
        replaceFragment(new AddIngredientFragment());

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepFrag.checkIfValidAndRead();
                stepList = stepFrag.getStepList();
                ingreList = ingreFrag.getIngreList();

                if (stepList.size() > 0 && ingreList.size() > 0) {
                    recipeName = etRecipeName.getText().toString();
                    if (!recipeName.equals("")) {
                        Recipe recipe = new Recipe(recipeName, publisher, ingreList, stepList);
                        addRecipeToDatabase(recipe);
                        hideKeyboardFrom(getActivity(),root);
                        etRecipeName.setText("");
                        replaceFragment(new AddIngredientFragment());
                        Toast.makeText(getActivity(), "Recipe uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please enter recipe name!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Add ingredients/steps first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    protected AddStepFragment getStepFrag() {
        return stepFrag;
    }

    protected AddIngredientFragment getIngreFrag() {
        return ingreFrag;
    }

    protected void setIngreFrag(AddIngredientFragment frag) {
        this.ingreFrag = frag;
    }

    protected void setStepFrag(AddStepFragment frag) {
        this.stepFrag = frag;
    }


    private void addRecipeToDatabase(Recipe recipe) {
        users_user_ref.child("my_recipes").child(recipe.getName()).setValue(recipe);
        recipes_sort_by_username_ref.child(publisher).child(recipe.getName()).setValue(recipe);
        recipes_sort_by_name_ref.child(recipe.getName()).setValue(recipe);
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutUpload, fragment);
        ft.commit();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}






































