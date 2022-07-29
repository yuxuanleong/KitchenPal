package com.example.kitchenpal.recipesFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.HandsFreeSteps;
import com.example.kitchenpal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecipeText extends Activity {
    private ImageButton back;
    private Button handsFree;
    private Button deleteBtn;
    private CardView recipeImage;
    private TextView tvIngre, tvStep, tvName, tvPublisher;
    private ArrayList<String> ingreList = new ArrayList<>();
    private ArrayList<String> stepList = new ArrayList<>();
    private DatabaseReference ingreRef, stepRef;
    private String ingrePara, stepPara, publisher, recipeName;

    public RecipeText() {
        //mandatory public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_text);

        //Initialise UI
        handsFree = (Button) findViewById(R.id.to_hands_free);
        back = (ImageButton) findViewById(R.id.back_button);
        recipeImage = (CardView) findViewById(R.id.recipeImageCardView);
        tvIngre = (TextView) findViewById(R.id.RecipeIngredients);
        tvStep = (TextView) findViewById(R.id.RecipeInstructions);
        tvName = (TextView) findViewById(R.id.recipe_name);
        tvPublisher = (TextView) findViewById(R.id.publishedBy);
        deleteBtn = findViewById(R.id.del_recipe);

        //get recipe name, publisher from recipe card view
        Intent intent = getIntent();
        recipeName = intent.getExtras().getString("name");
        publisher = intent.getExtras().getString("publisher");

        FirebaseHelper.getCurrUsernameData(new FirebaseSuccessListener() {
            @Override
            public void onDataFound(boolean isDataFetched) {
                if(isDataFetched) {
                    String getUsername = FirebaseHelper.getCurrUsername();
                    if(getUsername.equals(publisher)){
                        deleteBtn.setVisibility(View.VISIBLE);
                        
                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new Dialog(view.getContext());
                                dialog.setContentView(R.layout.confirm_del);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                                dialog.getWindow().setLayout(1050, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.setCancelable(false);

                                AppCompatButton del = dialog.findViewById(R.id.del_dialog_del);
                                AppCompatButton cancel = dialog.findViewById(R.id.del_dialog_cancel);
                                TextView itemName = dialog.findViewById(R.id.things_to_be_del);

                                itemName.setText(recipeName + "?");

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                del.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteItem(recipeName, publisher);
                                        dialog.dismiss();
                                        Toast.makeText(RecipeText.this, recipeName + " successfully deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dialog.show();
                            }
                        });
                    }
                }
            }
        });


        ingreRef = FirebaseDatabase.getInstance().getReference("recipes_sort_by_username")
                .child(publisher)
                .child(recipeName)
                .child("ingredientArrayList");
        stepRef = FirebaseDatabase.getInstance().getReference("recipes_sort_by_username")
                .child(publisher)
                .child(recipeName)
                .child("stepArrayList");

        //get ingredient list and step list from firebase
        getIngreListFromDatabase();
        getStepListFromDatabase();


        //display recipe name, publisher, ingredients and steps
        tvName.setText(recipeName);
        tvPublisher.setText(publisher);


        //set hands free button functionality
        handsFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecipeText.this, HandsFreeSteps.class);
                in.putExtra("step list", stepList);
                startActivity(in);
            }
        });

        //set back button functionality
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void deleteItem(String recipeName, String publisher) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("recipes_sort_by_recipe_name").child(recipeName).removeValue();

        ref.child("recipes_sort_by_username").child(publisher).child(recipeName).removeValue();

        ref.child("users").child(FirebaseHelper.getCurrUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()) {

                    if (dss.hasChild(recipeName)) {
                        dss.child(recipeName).getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIngreListFromDatabase() {
        ingreRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ingreList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        String ingre = dss.getValue(String.class);
                        ingreList.add(ingre);
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    for (String eachIngre : ingreList) {
                        stringBuilder.append(eachIngre).append("\n\n");
                    }

                    ingrePara = stringBuilder.toString();
                    tvIngre.setText(ingrePara);
                    //if there is extra line after the last ingredient
//                    ingreListPara = ingreListPara.substring(0, ingreListPara.length() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecipeText.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStepListFromDatabase() {
        stepRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    stepList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        String step = dss.getValue(String.class);
                        stepList.add(step);
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i < stepList.size(); i++) {
                        stringBuilder.append(i + 1+". ").append(stepList.get(i)).append("\n\n");
                    }

                    stepPara = stringBuilder.toString();
                    tvStep.setText(stepPara);
                    //if there is extra line after the last ingredient
//                    ingreListPara = ingreListPara.substring(0, ingreListPara.length() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
