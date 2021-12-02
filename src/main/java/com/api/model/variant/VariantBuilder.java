package com.api.model.variant;


public class VariantBuilder implements IVariantBuilder {
    private String id;
    private String sizeId;
    private String size;
    private double price;
    private double discountPrice;

    @Override
    public VariantBuilder addId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public VariantBuilder addDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    @Override
    public VariantBuilder addPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public VariantBuilder addSize(String size) {
        this.size = size;
        return this;
    }

    @Override
    public VariantBuilder addSizeId(String sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    @Override
    public VariantModel build() {
        return new VariantModel(id, sizeId, size, price, discountPrice);
    }
    
}
