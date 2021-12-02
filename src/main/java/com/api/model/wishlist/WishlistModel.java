package com.api.model.wishlist;

import java.util.ArrayList;

import com.api.model.product.ProductModel;

import org.bson.types.ObjectId;

public class WishlistModel {
    private ObjectId id;
    private ObjectId user;
    private ArrayList<ProductModel> products;

    public WishlistModel(ObjectId id, ObjectId user, ArrayList<ProductModel> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }

    public WishlistModel() {}; 

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

}
