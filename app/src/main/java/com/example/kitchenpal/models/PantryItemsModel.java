package com.example.kitchenpal.models;

public class PantryItemsModel {

    int image;
    String itemName;
    String publisher;
    String condition;
    boolean isRequested;

    public PantryItemsModel(int image, String itemName, String publisher, String condition, boolean bool) {
        this.image = image;
        this.itemName = itemName;
        this.publisher = publisher;
        this.condition = condition;
        this.isRequested = bool;
    }

    public int getImage() {
        return image;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isRequested() {
        return isRequested;
    }

    public void setCondition(boolean b) {
    }
}
