package com.example.kitchenpal.objects;

public class PantryItem {
    private String name;
    private String condition;
    private String publisher;

    public PantryItem() {

    }
    public PantryItem(String name, String condition, String publisher) {
        this.name = name;
        this.condition = condition;
        this.publisher = publisher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
