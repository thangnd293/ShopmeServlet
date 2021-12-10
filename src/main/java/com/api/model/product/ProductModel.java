package com.api.model.product;

import java.util.ArrayList;
import java.util.Date;

import com.api.model.filter.FilterModel;
import com.api.model.variant.VariantModel;

public class ProductModel {
    private String id;
    private String name;
    private ArrayList<String> categories;
    private String categoryPath;
    private ArrayList<String> imageCovers;
    private ArrayList<String> images;
    private String longDescription;
    private String shortDescription;
    private String color;
    private String brandId;
    private String brand;
    private ArrayList<String> filters;
    private ArrayList<FilterModel> facets;
    private double price;
    private double discountPrice;
    private String slug;
    private Date createAt;
    private Boolean isFeatured;
    private ArrayList<VariantModel> variants;
    private ArrayList<ProductModel> relateProducts;


    public ProductModel(String id, String name, ArrayList<String> categories, String categoryPath, double price, double discountPrice, ArrayList<String> imageCovers, ArrayList<String> images, String longDescription, String shortDescription, String color, String brandId, String brand, String slug, Boolean isFeatured, ArrayList<String> filters, ArrayList<FilterModel> facets, Date createAt, ArrayList<VariantModel> variants) {
        
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.brandId = brandId;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageCovers = imageCovers;
        this.images = images;
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
        this.categoryPath = categoryPath;
        this.isFeatured = isFeatured;
        this.slug = slug;
        this.filters = filters;
        this.facets = facets;
        this.createAt = createAt;
        this.color = color;
        this.variants = variants;
    }

    public ProductModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public ArrayList<String> getImageCovers() {
        return imageCovers;
    }

    public void setImageCovers(ArrayList<String> imageCovers) {
        this.imageCovers = imageCovers;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<String> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<String> filters) {
        this.filters = filters;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<FilterModel> getFacets() {
        return facets;
    }

    public void setFacets(ArrayList<FilterModel> facets) {
        this.facets = facets;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public ArrayList<VariantModel> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<VariantModel> variants) {
        this.variants = variants;
    }

    public Boolean isIsFeatured() {
        return isFeatured;
    }

    public ArrayList<ProductModel> getRelateProducts() {
        return relateProducts;
    }

    public void setRelateProducts(ArrayList<ProductModel> relateProducts) {
        this.relateProducts = relateProducts;
    }

}