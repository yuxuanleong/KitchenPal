package com.example.kitchenpal.objects;

import java.util.ArrayList;

public class User {
    private String username, email;
    private ArrayList<Recipe> recipes;

    public User(String username, String email, ArrayList<Recipe> recipes) {
        this.username = username;
        this.email = email;
        this.recipes = recipes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

