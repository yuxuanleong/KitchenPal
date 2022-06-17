package com.example.kitchenpal;

import java.io.Serializable;

public class Ingredient implements Serializable {

    String ingreName;

    public Ingredient() {
    }

    public Ingredient(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getIngredientName() {
        return ingreName;
    }

    public void setIngredientName(String ingreName) {
        this.ingreName = ingreName;
    }
}
