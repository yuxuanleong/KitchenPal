package com.example.kitchenpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


public class RecipeText extends Activity {
    private ImageButton back;
    private Button handsFree;
    private CardView recipeImage;
    //ingredients and instructions
    private TextView ingre;
    private TextView steps;

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
        ingre = (TextView) findViewById(R.id.RecipeIngredients);
        steps = (TextView) findViewById(R.id.RecipeInstructions);

        //set hands free button functionality
        handsFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecipeText.this, HandsFreeSteps.class);
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
}
