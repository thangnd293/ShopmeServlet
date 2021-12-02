package com.api.model.bill;

import org.bson.types.ObjectId;

public class ItemModel {
    private ObjectId id;
    private String name;
    private String sku;
    private double price;
    private int quantity;
    private double total;

    public ItemModel(ObjectId id, String name, String sku ,double price, int quantity, double total) {
        this.id = id;
        this.name =name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public ItemModel() {};

    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
