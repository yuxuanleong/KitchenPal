package com.example.kitchenpal.models;

public class ProfileMyRecipeModel {

    private Integer image;
    private String name;
    private String publisher;

    public ProfileMyRecipeModel(Integer image, String name, String publisher) {
        this.image = image;
        this.name = name;
        this.publisher = publisher;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
