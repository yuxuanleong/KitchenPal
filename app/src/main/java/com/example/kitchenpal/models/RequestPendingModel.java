package com.example.kitchenpal.models;

public class RequestPendingModel {

    int image;
    String itemName;
    String publisher;
    String condition;

    public RequestPendingModel(int image, String itemName, String publisher, String condition) {
        this.image = image;
        this.itemName = itemName;
        this.publisher = publisher;
        this.condition = condition;
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
}
