package com.api.dao.cart;

import com.api.model.cart.CartModel;

public interface ICartDAO {
    CartModel getOne(String userId);

    CartModel addOne(CartModel cart);

    CartModel updateOne(CartModel cart);
}
