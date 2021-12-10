package com.api.service.cart;

import java.util.ArrayList;

import com.api.dao.cart.CartDAO;
import com.api.model.cart.CartModel;
import com.api.model.cart.LineItem;
import com.api.model.product.ProductModel;
import com.api.service.product.ProductService;

import org.bson.types.ObjectId;

public class CartService implements ICartService {

    @Override
    public CartModel createCart(ObjectId userId) throws Exception {
        CartDAO cartDAO = new CartDAO();
        CartModel cart = new CartModel(userId, new ArrayList<LineItem>(), 0, 0);

        return cartDAO.addOne(cart);
    }

    @Override
    public CartModel getCart(ObjectId userId) throws Exception {
        if(userId == null) {
            throw new Exception("User does not exists");
        }

        CartDAO cartDAO = new CartDAO();
        CartModel cart = cartDAO.getOne(userId);
        if (cart == null) {
            cart = new CartModel(userId, new ArrayList<LineItem>(), 0, 0);
            cartDAO.addOne(cart);
        }

        return cart;
    }

    @Override
    public CartModel addToCart(ObjectId userId, String productVariantion, int quantity) throws Exception {
        if(userId == null) {
            throw new Exception("User does not exists");
        }

        CartModel cart = getCart(userId);
        boolean flag = false;
        for (LineItem item : cart.getItems()) {
            String variantId = item.getVariantId();

            if (variantId.equals(productVariantion)) {
                item.setQuantity(item.getQuantity() + quantity);
                flag = true;
                break;
            }
        }

        if (!flag) {
            LineItem item = new LineItem(null, productVariantion, quantity, 0, null);
            cart.getItems().add(item);
        }

        handleCartData(cart);
        CartDAO cartDAO = new CartDAO();
        cart = cartDAO.updateOne(cart);
        return cart;
    }

    @Override
    public CartModel updateItem(ObjectId userId, String productVariantion, int quantity) throws Exception {
        if(userId == null) {
            throw new Exception("User does not exists");
        }

        CartModel cart = getCart(userId);
        boolean isCartChange = false;
        for (LineItem item : cart.getItems()) {
            String variantId = item.getVariantId();

            if (variantId.equals(productVariantion)) {
                item.setQuantity(quantity);
                isCartChange = true;
                break;
            }
        }

        if (isCartChange) {
            handleCartData(cart);
            CartDAO cartDAO = new CartDAO();
            cart = cartDAO.updateOne(cart);
        }
        return cart;
    }

    @Override
    public CartModel removeItem(ObjectId userId, String productVariantion) throws Exception {
        if(userId == null) {
            throw new Exception("User does not exists");
        }
        
        CartModel cart = getCart(userId);
        int oldLength = cart.getItems().size();

        cart.getItems().removeIf(item -> item.getVariantId().equals(productVariantion));

        boolean isCartChange = oldLength != cart.getItems().size();
        if (isCartChange) {
            handleCartData(cart);
            CartDAO cartDAO = new CartDAO();
            cart = cartDAO.updateOne(cart);
        }

        return cart;
    }

    @Override
    public CartModel resetCart(ObjectId userId) throws Exception {
        if(userId == null) {
            throw new Exception("User does not exists");
        }
        
        CartDAO cartDAO = new CartDAO();
        CartModel cart = getCart(userId);
        cart.setItems(new ArrayList<LineItem>());
        cart.setQuantity(0);
        cart.setTotal(0);
        cart = cartDAO.updateOne(cart);

        return cart;
    }

    private void handleCartData(CartModel cart) throws Exception {
        ProductService productService = new ProductService();
        int qty = 0;
        double total = 0;
        int n = cart.getItems().size();
        for (int i = 0; i < n; i++) {
            LineItem item = cart.getItems().get(i);
            String variantId = item.getVariantId();

            ProductModel product = productService.getVariant(variantId);

            if (product == null) {
                cart.getItems().remove(i);
                i--;
                n--;
            }

            if (item.getQuantity() > 10) {
                throw new Exception("One item can only be purchased up to 10 pieces");
            }

            item.setProduct(product);
            item.setTotal(item.getQuantity() * product.getDiscountPrice());

            qty += item.getQuantity();
            total += item.getTotal();
        }

        cart.setQuantity(qty);
        cart.setTotal(total);
    }
}
