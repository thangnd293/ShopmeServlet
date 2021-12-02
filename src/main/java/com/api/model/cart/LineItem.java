package com.api.model.cart;

import com.api.model.product.ProductModel;

public class LineItem {
    private String variantId;
    private int quantity;
    private ProductModel product;
    private double total;
    
    public LineItem(String variantId, int quantity, double total, ProductModel product) {
        this.variantId = variantId;
        this.quantity = quantity;
        this.product = product;
        this.total = total;
    }

    public LineItem() {};
    
    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }

}
