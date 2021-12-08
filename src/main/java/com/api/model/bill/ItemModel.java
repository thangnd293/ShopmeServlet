package com.api.model.bill;

public class ItemModel {
    private String name;
    private String sku;
    private double price;
    private int quantity;
    private double total;

    public ItemModel(String name, String sku ,double price, int quantity, double total) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public ItemModel() {};

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
