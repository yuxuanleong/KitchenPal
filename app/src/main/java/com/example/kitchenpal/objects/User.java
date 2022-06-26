package com.example.kitchenpal.objects;

import java.util.ArrayList;

public class User {
    private String username, email;
    private ArrayList<Recipe> myRecipes, favourites;

    private enum proficiency {
        BEGINNER("Beginner"),
        INTERMEDIATE("Intermediate"),
        SEASONED("Seasoned"),
        EXPERIENCED("Experienced");

        private String proficiency;

        proficiency(String proficiency) {
            this.proficiency = proficiency;
        }

        public String getProficiency() {
            return this.proficiency;
        }
    }

    public User() {

    }
    public User(String username, String email, ArrayList<Recipe> myRecipes, ArrayList<Recipe> favourites) {
        this.username = username;
        this.email = email;
        this.myRecipes = myRecipes;
        this.favourites = favourites;
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

    public ArrayList<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(ArrayList<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
    }

    public ArrayList<Recipe> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Recipe> favourites) {
        this.favourites = favourites;
    }
}

