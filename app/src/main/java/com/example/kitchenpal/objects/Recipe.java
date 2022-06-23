package com.example.kitchenpal.objects;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private ArrayList<String> ingredientArrayList;
    private ArrayList<String> stepArrayList;

    public Recipe() {
    }

    public Recipe(String name, ArrayList<String> ingredientArrayList, ArrayList<String> stepArrayList) {
        this.name = name;
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
}
