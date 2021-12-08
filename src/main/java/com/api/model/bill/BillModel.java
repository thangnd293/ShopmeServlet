package com.api.model.bill;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

public class BillModel {
    private ObjectId user;
    private ObjectId id;
    private String _id;
    private String userId;
    private ShippingAddressModel shippingAddress;
    private ArrayList<ItemModel> items;
    private int quantity;
    private double amount;
    private Date createAt;

    public BillModel(ObjectId id, ObjectId user, ShippingAddressModel shippingAddress, ArrayList<ItemModel> items,
            int quantity,
            double amount, Date createAt) {
        this.id = id;
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.items = items;
        this.quantity = quantity;
        this.amount = amount;
        this.createAt = createAt;
    }

    public BillModel() {
    };

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ShippingAddressModel getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressModel shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
