package com.api.model.variant;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;


public class VariantMapping implements IMapping<VariantModel> {
    public static final VariantModel map(JsonObject object) {
        String sizeId = object.get("sizeId") != null ? object.get("sizeId").getAsString() : null;
        double price = object.get("price") != null ? object.get("price").getAsDouble() : -1;
        double discountPrice = object.get("discountPrice") != null ? object.get("discountPrice").getAsDouble() : -1;
         
        return new VariantBuilder().addSizeId(sizeId).addPrice(price).addDiscountPrice(discountPrice).build();
    }
}
