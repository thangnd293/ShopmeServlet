package com.api.dao.cart;

import com.api.model.cart.CartModel;

import org.bson.types.ObjectId;

public interface ICartDAO {
    CartModel getOne(ObjectId userId);

    CartModel addOne(CartModel cart);

    CartModel updateOne(CartModel cart);
}
