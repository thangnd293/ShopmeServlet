package com.api.model.product;

import java.util.ArrayList;
import java.util.Date;

import com.api.model.filter.FilterModel;
import com.api.model.variant.VariantModel;

public interface IProductBuilder {
    ProductBuilder addId(String id);

    ProductBuilder addCategories(ArrayList<String> categories);
    
    ProductBuilder addName(String name);

    ProductBuilder addBrand(String brand);

    ProductBuilder addBrandName(String brandName);

    ProductBuilder addColor(String color);

    ProductBuilder addPrice(double price);

    ProductBuilder addDiscountPrice(double discountPrice);

    ProductBuilder addImageCovers (ArrayList<String> imageCovers);

    ProductBuilder addImages(ArrayList<String> images);

    ProductBuilder addLongDescription(String longDescription);

    ProductBuilder addShortDescription(String shortDescription);

    ProductBuilder addIsFeatured(Boolean isFeatured);

    ProductBuilder addSlug(String slug);

    ProductBuilder addFilters(ArrayList<String> filters);

    ProductBuilder addFacets(ArrayList<FilterModel> facets);

    ProductBuilder addCreateAt(Date createAt);

    ProductBuilder addVariants(ArrayList<VariantModel> variants);

    ProductModel build();
}
