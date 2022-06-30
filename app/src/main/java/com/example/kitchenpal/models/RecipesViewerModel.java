package com.example.kitchenpal.models;

import android.widget.ToggleButton;

public class RecipesViewerModel {

    int image;
    String recipeName;
    String publisher;
    boolean isFavourite;

    //constructor
    public RecipesViewerModel(int image, String recipeName, String publisher, boolean bool) {
        this.image = image;
        this.recipeName = recipeName;
        this.publisher = publisher;
        this.isFavourite = bool;
    }


    //getters
    public int getImage() {
        return image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

}
