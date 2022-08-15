package com.example.kitchenpal;

import static org.junit.Assert.*;

import com.example.kitchenpal.helper.Methods;
import com.example.kitchenpal.objects.Recipe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeTest {

    Recipe recipeNoIngreStep = new Recipe("recipe name", "publisher", null, null);
    Recipe recipeNoIngre = new Recipe("recipe name", "publisher", null, new ArrayList<>(Arrays.asList("one step", "two steps")));
    Recipe recipeNoStep = new Recipe("recipe name", "publisher", new ArrayList<>(Arrays.asList("one ingre")), null);
    Recipe recipeNoRecipeName = new Recipe("", "publisher", new ArrayList<>(Arrays.asList("one ingre")), new ArrayList<>(Arrays.asList("one step", "two steps")));
    Recipe recipeNoPublisher = new Recipe("recipe name", "", new ArrayList<>(Arrays.asList("one ingre")), new ArrayList<>(Arrays.asList("one step", "two steps")));
    Recipe recipeCorrect = new Recipe("recipe name", "publisher", new ArrayList<>(Arrays.asList("one ingre")), new ArrayList<>(Arrays.asList("one step", "two steps")));


    Methods methods = new Methods();

    @Test
    public void checkRecipeValid() {
        boolean resultNoIngreStep = methods.isValidRecipe(recipeNoIngreStep);
        assertFalse(resultNoIngreStep);

        boolean resultNoIngre = methods.isValidRecipe(recipeNoIngre);
        assertFalse(resultNoIngre);

        boolean resultNoStep = methods.isValidRecipe(recipeNoStep);
        assertFalse(resultNoStep);

        boolean resultNoRecipeName = methods.isValidRecipe(recipeNoRecipeName);
        assertFalse(resultNoRecipeName);

        boolean resultNoPublisher = methods.isValidRecipe(recipeNoPublisher);
        assertFalse(resultNoPublisher);

        boolean resultCorrect = methods.isValidRecipe(recipeCorrect);
        assertTrue(resultCorrect);
    }
}