package com.api.service.wishlist;

import com.api.model.wishlist.WishlistModel;

import org.bson.types.ObjectId;

public interface IWishListService {
    WishlistModel getWishList(ObjectId userID) throws Exception;

    WishlistModel toggleItem(ObjectId userId, String productId) throws Exception;
}
