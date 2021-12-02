package com.api.model.bill;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

public class BillModel {
    private ObjectId user;
    private ShippingAddressModel shippingAdress;
    private ArrayList<ItemModel> items;
    private int quantity;
    private double amount;
    private Date createAt;

    public BillModel(ObjectId user, ShippingAddressModel shippingAdress, ArrayList<ItemModel> items, int quantity, double amount, Date createAt) {
        this.user = user;
        this.shippingAdress = shippingAdress;
        this.items = items;
        this.quantity = quantity;
        this.amount = amount;
        this.createAt = createAt;
    }

    public BillModel() {};

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ShippingAddressModel getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(ShippingAddressModel shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public ArrayList<ItemModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemModel> items) {
        this.items = items;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
