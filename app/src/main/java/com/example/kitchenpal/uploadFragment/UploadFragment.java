package com.example.kitchenpal.uploadFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kitchenpal.R;
import com.example.kitchenpal.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String recipeName;
    Button buttonSubmit;
    FirebaseDatabase myDatabase;
    DatabaseReference userRecipeRef;
    AddStepFragment stepFrag = new AddStepFragment();;
    AddIngredientFragment ingreFrag = new AddIngredientFragment();;
    ArrayList<String> ingreList, stepList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);

        myDatabase = FirebaseDatabase.getInstance();
        userRecipeRef = myDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser()
                .getUid()).child("recipes");

        etRecipeName = root.findViewById(R.id.etRecipeName);
        buttonSubmit = root.findViewById(R.id.btnSubmit);

//        getChildFragmentManager().beginTransaction().add(R.id.frameLayoutUpload, new AddIngredientFragment(), "addIngreFragment").commit();
//        getChildFragmentManager().beginTransaction().add(R.id.frameLayoutUpload, new AddStepFragment(), "addStepFragment").commit();

//        stepFrag = (AddStepFragment) getChildFragmentManager().findFragmentByTag("addStepFragment");
//        ingreFrag = (AddIngredientFragment) getChildFragmentManager().findFragmentByTag("addIngreFragment");

        replaceFragment(ingreFrag);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepFrag.checkIfValidAndRead();
                stepList = stepFrag.getStepList();
                ingreList = ingreFrag.getIngreList();
                recipeName = etRecipeName.getText().toString();
                Recipe recipe = new Recipe(recipeName, ingreList, stepList);
                addRecipeToDatabase(recipe);
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


    private void addRecipeToDatabase (Recipe recipe) {
        userRecipeRef.child(recipe.getName()).setValue(recipe);
//        userRecipeRef.child("Ingredients").setValue(recipe.getIngredientArrayList());
//        userRecipeRef.child("Steps").setValue(recipe.getStepArrayList());
    }
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutUpload, fragment);
        ft.commit();
    }
}