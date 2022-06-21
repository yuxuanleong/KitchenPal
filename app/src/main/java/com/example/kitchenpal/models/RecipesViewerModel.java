package com.example.kitchenpal.models;

public class RecipesViewerModel {
    int image;
    String recipeName;
    String publisher;

    //constructor
    public RecipesViewerModel(int image, String recipeName, String publisher) {
        this.image = image;
        this.recipeName = recipeName;
        this.publisher = publisher;
    }

    public int getImage() {
        return image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getPublisher() {
        return publisher;
    }
}
