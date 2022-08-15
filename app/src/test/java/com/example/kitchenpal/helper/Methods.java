package com.example.kitchenpal.helper;

import com.example.kitchenpal.objects.PantryItem;
import com.example.kitchenpal.objects.Recipe;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Methods {

    public boolean isValidRecipe(Recipe recipe) {
        String name = recipe.getName();
        String publisher = recipe.getPublisher();
        ArrayList<String> ingreList = recipe.getIngredientArrayList();
        ArrayList<String> stepList = recipe.getStepArrayList();

        if (ingreList == null || stepList  == null) {
            return false;
        }
        return !name.isEmpty() && !publisher.isEmpty() && ingreList.size() > 0 && stepList.size() > 0;
    }

    public boolean isValidPantryItem(PantryItem pantryItem) {
        String name = pantryItem.getName();
        String publisher = pantryItem.getPublisher();
        String condition = pantryItem.getCondition();

        return !name.isEmpty() && !publisher.isEmpty() && !condition.isEmpty();
    }

    public boolean patternMatches(String email) {

        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }
}
