package com.example.kitchenpal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link UploadFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class UploadFragment extends Fragment implements View.OnClickListener {

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

    LinearLayout layout;
    Button buttonAdd;
    Button buttonSubmit;

    ArrayList<Ingredient> ingreList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);

        layout = root.findViewById(R.id.layout_list);
        buttonAdd = root.findViewById(R.id.buttonAdd);
        buttonSubmit = root.findViewById(R.id.buttonSubmitIngre);

        buttonAdd.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                addView();
                break;
            case R.id.buttonSubmitIngre:
//                if (checkIfValidAndRead()) {
//                    // TODO: send data to database
//                }
                break;
        }
    }

    private boolean checkIfValidAndRead() {
        ingreList.clear();
        boolean result = true;

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            EditText etIngre = v.findViewById(R.id.etIngredient);
            String ingreName = etIngre.getText().toString();

            Ingredient ingre = new Ingredient();

            if (!ingreName.equals("")) {
                ingre.setIngredientName(ingreName);
            } else {
                result = false;
                break;
            }

            ingreList.add(ingre);
        }

        if (ingreList.size() == 0) {
            result = false;
            Toast.makeText(getActivity(), "Add ingredient(s) first!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }



    private void addView() {
        View v = getLayoutInflater().inflate(R.layout.row_add_ingre,null, false);
        EditText etIngre = v.findViewById(R.id.etIngredient);
        ImageView imgRemove = v.findViewById(R.id.imageRemove);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(v);
            }
        });
        layout.addView(v);
    }

    private void removeView(View view) {
        layout.removeView(view);
    }

}