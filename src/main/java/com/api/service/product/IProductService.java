package com.api.service.product;

import java.util.ArrayList;

import com.api.model.product.ProductModel;

public interface IProductService {

    ProductModel getProduct(String id) throws Exception;

    ProductModel addProduct(ProductModel product) throws Exception;

    ArrayList<ProductModel> getAllProduct();

    ArrayList<ProductModel> getProductFeatures();

    ArrayList<ProductModel> getAllProduct(String categoryId, String[] filter, String sortParam) throws Exception ;

    ProductModel updateProduct(String id, ProductModel product) throws Exception;

    void deleteProduct(String id) throws Exception;

    ProductModel getVariant(String id) throws Exception;

}
