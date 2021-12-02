package com.api.service.variant;

import com.api.model.product.ProductModel;

public interface IVariantService {
    ProductModel getVariant(String id) throws Exception;
}
