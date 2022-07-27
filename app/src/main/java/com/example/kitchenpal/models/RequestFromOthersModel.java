package com.example.kitchenpal.models;

public class RequestFromOthersModel {

    int image;
    String itemName;
    String requester;
    String condition;

    public RequestFromOthersModel(int image, String itemName, String requester, String condition) {
        this.image = image;
        this.itemName = itemName;
        this.requester = requester;
        this.condition = condition;
    }

    public int getImage() {
        return image;
    }

    public String getItemName() {
        return itemName;
    }

    public String getRequester() {
        return requester;
    }

    public String getCondition() {
        return condition;
    }
}
