package com.api.service.cart;

import com.api.model.cart.CartModel;

import org.bson.types.ObjectId;

public interface ICartService {
    CartModel getCart(ObjectId user) throws Exception;

    CartModel createCart(ObjectId user) throws Exception;

    CartModel addToCart(ObjectId user, String productVariantion, int quantity) throws Exception;

    CartModel updateItem(ObjectId user, String productVariantion, int quantity) throws Exception;

    CartModel removeItem(ObjectId user, String productVariantion) throws Exception;

    CartModel resetCart(ObjectId user) throws Exception;

}
