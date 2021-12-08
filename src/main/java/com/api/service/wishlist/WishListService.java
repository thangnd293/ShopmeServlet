package com.api.service.wishlist;

import com.api.dao.wishlist.WishListDAO;
import com.api.model.product.ProductModel;
import com.api.model.wishlist.WishlistModel;
import com.api.service.product.ProductService;

import org.bson.types.ObjectId;

public class WishListService implements IWishListService {
    @Override
    public WishlistModel getWishList(ObjectId userId) throws Exception {
        if (userId == null) {
            throw new Exception("Product does not exist!!");
        }

        WishListDAO wishListDAO = new WishListDAO();
        WishlistModel wishList = wishListDAO.getOne(userId);
        return wishList;
    }

    @Override
    public WishlistModel toggleItem(ObjectId userId, String productId) throws Exception {
        if (productId == null || userId == null) {
            throw new Exception("Product does not exist!!");
        }

        WishlistModel wishList = getWishList(userId);

        boolean isRemove = wishList.getProducts().removeIf(product -> product.getId().equals(productId));

        if (!isRemove) {
            ProductService productService = new ProductService();
            ProductModel product = productService.getProduct(productId);
            wishList.getProducts().add(product);
        }

        WishListDAO wishListDAO = new WishListDAO();
        wishList = wishListDAO.updateOne(wishList.getId(), wishList);

        return wishList;
    }
}
