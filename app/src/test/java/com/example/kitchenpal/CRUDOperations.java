package com.example.kitchenpal;


import com.example.kitchenpal.objects.PantryItem;
import com.firebase.client.Firebase;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test for ProductsService.
 */
@RunWith(MockitoJUnitRunner.class)
public class CRUDOperations {
    private Firebase firebaseNode;

    public CRUDOperations(Firebase firebase) {
        this.firebaseNode = firebase.child("pantry");
    }

    public void save(PantryItem product) {
        firebaseNode.child(product.getName()).setValue(product);
    }
}