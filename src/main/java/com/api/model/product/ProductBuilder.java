package com.api.model.product;

import java.util.ArrayList;
import java.util.Date;

import com.api.model.filter.FilterModel;
import com.api.model.variant.VariantModel;

public class ProductBuilder implements IProductBuilder {

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
    private String brandName;
    private ArrayList<String> filters;
    private ArrayList<FilterModel> facets;
    private double price;
    private double discountPrice;
    private String slug;
    private Date createAt;
    private Boolean isFeatured;
    private ArrayList<VariantModel> variants;


    @Override
    public ProductBuilder addId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductBuilder addCategories(ArrayList<String> categories) {
        this.categories = categories;
        return this;
    }

        @Override
    public ProductBuilder addImages(ArrayList<String> images) {
        this.images = images;
        return this;
    }

    @Override
    public ProductBuilder addImageCovers(ArrayList<String> imageCovers) {
        this.imageCovers = imageCovers;
        return this;
    }

    @Override
    public ProductBuilder addLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    @Override
    public ProductBuilder addShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    @Override
    public ProductBuilder addBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    @Override
    public ProductBuilder addColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public ProductBuilder addCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    @Override
    public ProductBuilder addBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    @Override
    public ProductBuilder addDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    @Override
    public ProductBuilder addFilters(ArrayList<String> filters) {
        this.filters = filters;
        return this;
    }

    @Override
    public ProductBuilder addFacets(ArrayList<FilterModel> facets) {
        this.facets = facets;
        return this;
    }

    @Override
    public ProductBuilder addIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
        return this;
    }

    @Override
    public ProductBuilder addPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public ProductBuilder addSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @Override
    public ProductBuilder addName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProductBuilder addVariants(ArrayList<VariantModel> variants) {
        this.variants = variants;
        return this;
    }

    @Override
    public ProductModel build() {
        return new ProductModel(id, name, categories, categoryPath, price, discountPrice, imageCovers, images, longDescription, shortDescription, color, brandId, brandName, slug, isFeatured, filters, facets, createAt, variants);
    }
}
