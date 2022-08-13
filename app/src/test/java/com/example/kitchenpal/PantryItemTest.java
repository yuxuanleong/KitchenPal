package com.example.kitchenpal;

import static org.junit.Assert.*;

import com.example.kitchenpal.objects.PantryItem;

import org.junit.Test;

public class PantryItemTest {

    PantryItem itemNoName = new PantryItem("", "Opened", "publisher");
    PantryItem itemNoCondition = new PantryItem("item name", "", "publisher");
    PantryItem itemNoPublisher = new PantryItem("item name", "Brand new", "");
    PantryItem itemCorrect = new PantryItem("item name", "Opened", "publisher");

    Methods methods = new Methods();

    @Test
    public void checkPantryItemValid() {
        boolean resultNoName = methods.isValidPantryItem(itemNoName);
        assertFalse(resultNoName);

        boolean resultNoCondition = methods.isValidPantryItem(itemNoCondition);
        assertFalse(resultNoCondition);

        boolean resultNoPublisher = methods.isValidPantryItem(itemNoPublisher);
        assertFalse(resultNoPublisher);

        boolean resultCorrect = methods.isValidPantryItem(itemCorrect);
        assertTrue(resultCorrect);
    }
}
