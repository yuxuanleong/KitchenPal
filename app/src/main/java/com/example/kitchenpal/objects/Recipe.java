package com.example.kitchenpal.objects;

import java.util.ArrayList;

public class Recipe {

    private String name, publisher;
    private ArrayList<String> ingredientArrayList, stepArrayList;

    public Recipe() {
    }

    public Recipe(String name, String publisher, ArrayList<String> ingredientArrayList, ArrayList<String> stepArrayList) {
        this.name = name;
        this.publisher = publisher;
        this.ingredientArrayList = ingredientArrayList;
        this.stepArrayList = stepArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredientArrayList() {
        return ingredientArrayList;
    }

    public void setIngredientArrayList(ArrayList<String> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    public ArrayList<String> getStepArrayList() {
        return stepArrayList;
    }

    public void setStepArrayList(ArrayList<String> stepArrayList) {
        this.stepArrayList = stepArrayList;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
