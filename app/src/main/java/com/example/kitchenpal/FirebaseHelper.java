package com.example.kitchenpal;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseHelper {

    static FirebaseAuth auth = FirebaseAuth.getInstance();
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    static String currUsername = "";
    static String UID = "";
    static String contact = "";


    public FirebaseHelper() {
    }

    public static void getCurrUsernameData(FirebaseSuccessListener callback) {
        ref.child("users").child(getCurrUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUsername = snapshot.child("username").getValue(String.class);
                callback.onDataFound(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "failed to retrieve username from Firebase");
            }
        });
    }

    public static String getCurrUsername() {
        return currUsername;
    }

    public static void getUIDData(String username, FirebaseSuccessListener callback) {
        ref.child("users_sort_by_username").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UID = snapshot.child("UID").getValue(String.class);
                callback.onDataFound(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static String getUID() {
        return UID;
    }


    public static void getContactData(String username, FirebaseSuccessListener callback) {
        ref.child("users_sort_by_username").child(username).child("contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    contact = snapshot.getValue(String.class);
                    if (contact.isEmpty()) {
                        callback.onDataFound(false);
                    } else {
                        callback.onDataFound(true);
                    }
                } else {
                    callback.onDataFound(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static String getContact() {
        return contact;
    }
    public static String getCurrUserID() {
        return auth.getCurrentUser().getUid();
    }
}
