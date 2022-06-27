package com.example.kitchenpal.uploadFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.RecipesViewerModel;
import com.example.kitchenpal.objects.Recipe;
import com.example.kitchenpal.objects.User;
import com.example.kitchenpal.profileFragment.ProfileMyRecipesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link UploadFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class UploadFragment extends Fragment {

//
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//
//    private String mParam1;
//    private String mParam2;

    public UploadFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment UploadFragment.
//     */
//
//    public static UploadFragment newInstance(String param1, String param2) {
//        UploadFragment fragment = new UploadFragment();
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

    EditText etRecipeName;
    String recipeName, publisher;
    Button buttonSubmit;
    FirebaseAuth mAuth;
    FirebaseDatabase myDatabase;
    DatabaseReference users_user_ref, recipes_sort_by_username_ref, recipes_sort_by_name_ref;
    AddStepFragment stepFrag = null;
    AddIngredientFragment ingreFrag = null;
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
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    publisher = user.getUsername();
                }
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


    private void addRecipeToDatabase (Recipe recipe) {
        users_user_ref.child("my_recipes").child(recipe.getName()).setValue(recipe);
        recipes_sort_by_username_ref.child(publisher).child(recipe.getName()).setValue(recipe);
        recipes_sort_by_name_ref.child(recipe.getName()).setValue(recipe);
//        myRecipesFragment.addRecipeToMyRecipe(new RecipesViewerModel(R.drawable.burger, recipe.getName(), recipe.getPublisher()));
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutUpload, fragment);
        ft.commit();
    }
}