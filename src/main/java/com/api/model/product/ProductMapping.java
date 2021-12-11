package com.api.model.product;

import java.util.ArrayList;

import com.api.model.common.IMapping;
import com.api.model.variant.VariantMapping;
import com.api.model.variant.VariantModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProductMapping implements IMapping<ProductModel> {
  public static final ProductModel map(JsonObject object) {

    String name = object.get("name") != null ? object.get("name").getAsString() : null;

    ArrayList<String> categories = null;
    JsonArray jsonCategories = object.getAsJsonArray("categories");
    if (jsonCategories != null) {
      categories = new ArrayList<String>();
      for (int i = 0; i < jsonCategories.size(); i++) {
        String str = jsonCategories.get(i).getAsString();
        categories.add(str);
      }
    }

    ArrayList<String> imageCovers = null;
    JsonArray jsonImageCovers = object.getAsJsonArray("imageCovers");
    if (jsonImageCovers != null) {
      imageCovers = new ArrayList<String>();
      for (int i = 0; i < jsonImageCovers.size(); i++) {
        String str = jsonImageCovers.get(i).getAsString();
        imageCovers.add(str);
      }
    }

    ArrayList<String> images = null;
    JsonArray jsonImage = object.getAsJsonArray("images");
    if (jsonImage != null) {
      images = new ArrayList<String>();
      for (int i = 0; i < jsonImage.size(); i++) {
        String str = jsonImage.get(i).getAsString();
        images.add(str);
      }
    }

    String longDescription = object.get("longDescription") != null ? object.get("longDescription").getAsString() : null;

    String shortDescription = object.get("shortDescription") != null ? object.get("shortDescription").getAsString()
        : null;

    String color = object.get("color") != null ? object.get("color").getAsString() : null;
    String brandId = object.get("brandId") != null ? object.get("brandId").getAsString() : null;

    Boolean isFeatured = object.get("isFeatured") != null ? object.get("isFeatured").getAsBoolean() : false;

    ArrayList<String> filters = null;
    JsonArray jsonFilters = object.getAsJsonArray("filters");
    if (jsonFilters != null) {
      filters = new ArrayList<String>();
      for (int i = 0; i < jsonFilters.size(); i++) {
        String str = jsonFilters.get(i).getAsString();
        filters.add(str);
      }
    }

    ArrayList<VariantModel> variants = null;
    JsonArray jsonVariants = object.getAsJsonArray("variants");
    if (jsonVariants != null) {
      variants = new ArrayList<VariantModel>();
      for (int i = 0; i < jsonVariants.size(); i++) {
        JsonObject jsonVariant = jsonVariants.get(i).getAsJsonObject();

        VariantModel v = VariantMapping.map(jsonVariant);
        variants.add(v);
      }
    }

    // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    // String dateString = object.get("createAt").getAsString();
    // Date createAt = new Date();
    // try {
    // createAt = dateFormat.parse(dateString);
    // } catch (Exception e) {}
    // boolean isFeatured = object.get("isFeatured").getAsBoolean();

    // return new ProductBuilder()
    // .addId(id)
    // .addCategories(categories)
    // .addColor(color)
    // .addCreateAt(createAt)
    // .addDiscountPrice(discountPrice)
    // .addFilters(filters)
    // .addImageCovers(imageCovers)
    // .addImages(images)
    // .addIsFeatured(isFeatured)
    // .addLongDescription(longDescription)
    // .addPrice(price)
    // .addShortDescription(shortDescription)
    // .addSlug(slug)
    // .addName(name)
    // .build();

    return new ProductBuilder()
        .addCategories(categories)
        .addColor(color)
        .addBrandId(brandId)
        .addFilters(filters)
        .addImageCovers(imageCovers)
        .addImages(images)
        .addLongDescription(longDescription)
        .addShortDescription(shortDescription)
        .addName(name)
        .addVariants(variants)
        .addIsFeatured(isFeatured)
        .build();
  }
}
