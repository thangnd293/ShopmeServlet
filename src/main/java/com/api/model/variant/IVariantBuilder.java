package com.api.model.variant;


public interface IVariantBuilder {
    VariantBuilder addId(String id);

    VariantBuilder addSizeId(String sizeId);

    VariantBuilder addSize(String size);

    VariantBuilder addPrice(double price);

    VariantBuilder addDiscountPrice(double discountPrice);

    VariantModel build();
}
