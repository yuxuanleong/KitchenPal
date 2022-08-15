package com.example.kitchenpal;


import static com.example.kitchenpal.helper.FirebaseAssertions.assertFirebaseData;

import com.example.kitchenpal.helper.CRUDOperations;
import com.example.kitchenpal.helper.FirebaseMocker;
import com.example.kitchenpal.objects.PantryItem;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CRUDTest extends TestCase {
    final FirebaseMocker firebaseMocker = new FirebaseMocker();
    final CRUDOperations productsService = new CRUDOperations(firebaseMocker.getFirebase());

    @Before
    public void clearFirebase() {
        firebaseMocker.getFirebase().child("pantry").removeValue();
    }

    @After
    public void flush() throws InterruptedException {
        firebaseMocker.setValueSync("finished", "ok");
    }

    @Test
    public void testCreate() throws Exception {
        PantryItem product = new PantryItem("chocolate", "opened", "publisher");
        productsService.save(product);

        assertFirebaseData(firebaseMocker.getFirebase(), "pantry/chocolate", product);
    }

}
