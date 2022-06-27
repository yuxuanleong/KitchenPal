package com.example.kitchenpal.uploadFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
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
// * Use the {@link AddStepFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddStepFragment extends Fragment implements View.OnClickListener {

//
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//
//    private String mParam1;
//    private String mParam2;
//
//    public AddStepFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddStepFragment.
//     */
//    //
//    public static AddStepFragment newInstance(String param1, String param2) {
//        AddStepFragment fragment = new AddStepFragment();
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
    Button buttonBack, buttonAdd;
    LinearLayout layoutStep;
    UploadFragment parentFrag;
    ArrayList<String> stepList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_step, container, false);


        buttonBack = root.findViewById(R.id.btnBackToIngre);
        buttonAdd = root.findViewById(R.id.buttonAddStep);
        layoutStep = (LinearLayout) root.findViewById(R.id.linearLayout_list_steps);
        parentFrag = (UploadFragment) getParentFragment();

        buttonBack.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        addStepView();

        return root;
    }

    protected ArrayList<String> getStepList() {
        return stepList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddStep:
                addStepView();
                break;
            case R.id.btnBackToIngre:
                parentFrag.setStepFrag(this);
                parentFrag.replaceFragment(parentFrag.getIngreFrag());
                break;
        }
    }

    protected boolean checkIfValidAndRead() {
        stepList.clear();
        boolean result = true;

        for (int i = 0; i < layoutStep.getChildCount(); i++) {
            View v = layoutStep.getChildAt(i);
            EditText etStep = v.findViewById(R.id.etStep);
            String stepDesc = etStep.getText().toString();


            if (!stepDesc.equals("")) {
                stepList.add(stepDesc);
            } else {
                result = false;
                break;
            }
        }

        if (stepList.size() == 0) {
            result = false;
            Toast.makeText(getActivity(), "Add step(s) first!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addStepView() {
        View v = getLayoutInflater().inflate(R.layout.row_add_step,null, false);
        EditText etStep = v.findViewById(R.id.etStep);
        ImageView imgRemoveStep = v.findViewById(R.id.imageRemoveStep);

        imgRemoveStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStepView(v);
            }
        });
        layoutStep.addView(v);
    }

    private void removeStepView(View view) { layoutStep.removeView(view); }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.stepFrameLayout, fragment);
        ft.commit();
    }

}