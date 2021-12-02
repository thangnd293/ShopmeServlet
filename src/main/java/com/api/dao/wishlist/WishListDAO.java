package com.api.dao.wishlist;

import com.api.config.database.DatabaseConnect;
import com.api.model.product.ProductModel;
import com.api.model.wishlist.WishlistModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class WishListDAO implements IWishListDAO {
    private MongoCollection<WishlistModel> wishlistCollection = new DatabaseConnect().getCollection("wishlist",
            WishlistModel.class);

    @Override
    public WishlistModel getOne(ObjectId userId) {
        WishlistModel wishList = wishlistCollection.find(eq("user", userId)).first();
        if(wishList == null) {
            wishList = new WishlistModel(null, userId, new ArrayList<ProductModel>());
            wishlistCollection.insertOne(wishList);
        }
        return wishList;
    }

    @Override
    public WishlistModel updateOne(ObjectId id, WishlistModel newWislList) {
        return wishlistCollection.findOneAndReplace(eq("_id", id), newWislList, new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));
    }
}
