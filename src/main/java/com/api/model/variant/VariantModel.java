package com.api.model.variant;



public class VariantModel {
    private String id;
    private String sizeId;
    private String size;
    private double price;
    private double discountPrice;

    public VariantModel(String id, String sizeId, String size, double price, double discountPrice) {
        this.id = id;
        this.sizeId = sizeId;
        this.size = size;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public VariantModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

}
