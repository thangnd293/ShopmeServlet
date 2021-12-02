package com.api.service.variant;

import com.api.dao.product.ProductDAO;
import com.api.model.product.ProductModel;

public class VariantService implements IVariantService {
    @Override
    public ProductModel getVariant(String id) throws Exception {
        ProductDAO productDAO = new ProductDAO();
        try {
            ProductModel product = productDAO.getVariant(id);
            if (product == null) {
                throw new Exception();
            }
            return product;

        } catch (Exception e) {
            throw new Exception("Variant does not exist!!");
        }
    }
}
