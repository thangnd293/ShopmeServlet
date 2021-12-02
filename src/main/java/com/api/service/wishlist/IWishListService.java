package com.api.service.wishlist;

import com.api.model.wishlist.WishlistModel;

public interface IWishListService {
    WishlistModel getWishList(String userId) throws Exception;

    WishlistModel toggleItem(String userId, String productId) throws Exception;
}
