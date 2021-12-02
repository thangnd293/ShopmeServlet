package com.api.dao.wishlist;

import com.api.model.wishlist.WishlistModel;

import org.bson.types.ObjectId;

public interface IWishListDAO {
    WishlistModel getOne(ObjectId userId);

    WishlistModel updateOne(ObjectId id, WishlistModel newWishList);
}
