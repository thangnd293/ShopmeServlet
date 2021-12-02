package com.api.service.cart;

import com.api.model.cart.CartModel;

public interface ICartService {
    CartModel getCart(String userId) throws Exception;

    CartModel createCart(String userId) throws Exception;

    CartModel addToCart(String userId, String productVariantion, int quantity) throws Exception;

    CartModel updateCart(String userId, String productVariantion, int quantity) throws Exception;

    CartModel removeItem(String userId, String productVariantion) throws Exception;
}
