package com.api.model.cart;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class CartModel {
    private ObjectId id;
    private ObjectId user;
    private ArrayList<LineItem> items;
    private double total;
    private int quantity;

    public CartModel(ObjectId user, ArrayList<LineItem> items, double total, int quantity) {
        this.user = user;
        this.items = items;
        this.total = total;
        this.quantity = quantity;
    }

    public CartModel() {}

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<LineItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
