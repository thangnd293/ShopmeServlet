package com.api.dao.cart;

import com.api.config.database.DatabaseConnect;
import com.api.model.cart.CartModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import static com.mongodb.client.model.Filters.eq;

import org.bson.types.ObjectId;

public class CartDAO implements ICartDAO {
    private MongoCollection<CartModel> cartCollection = new DatabaseConnect().getCollection("cart", CartModel.class);

    @Override
    public CartModel getOne(String userId) {
        CartModel cart = cartCollection.find(eq("user", new ObjectId(userId))).first();
        return cart;
    }

    @Override
    public CartModel addOne(CartModel cart) {
        cartCollection.insertOne(cart);
        return cart;
    }

    @Override
    public CartModel updateOne(CartModel cart) {
        return cartCollection.findOneAndReplace(eq("_id", cart.getId()), cart, new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));
    }
}
