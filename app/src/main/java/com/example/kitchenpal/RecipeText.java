package com.example.kitchenpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecipeText extends Activity {
    private ImageButton back;
    private Button handsFree;
    private CardView recipeImage;
    private TextView tvIngre, tvStep, tvName, tvPublisher;
    private ArrayList<String> ingreList = new ArrayList<>();
    private ArrayList<String> stepList = new ArrayList<>();
    private DatabaseReference ingreRef, stepRef;
    private String ingrePara, stepPara;

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


        //get recipe name, publisher from recipe card view
        Intent intent = getIntent();
        String recipeName = intent.getExtras().getString("name");
        String publisher = intent.getExtras().getString("publisher");


        //get ingredient list and step list from firebase
        ingreRef = FirebaseDatabase.getInstance().getReference("recipes_sort_by_username")
                .child(publisher)
                .child(recipeName)
                .child("ingredientArrayList");
        stepRef = FirebaseDatabase.getInstance().getReference("recipes_sort_by_username")
                .child(publisher)
                .child(recipeName)
                .child("stepArrayList");

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
