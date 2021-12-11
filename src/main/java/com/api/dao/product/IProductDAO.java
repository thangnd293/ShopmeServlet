package com.api.dao.product;

import java.util.ArrayList;

import com.api.model.product.ProductModel;
import com.mongodb.BasicDBObject;

import org.bson.conversions.Bson;

public interface IProductDAO {
    ProductModel getOne(String id);

    ProductModel addOne(ProductModel product);

    ArrayList<ProductModel> getAll();

    ArrayList<ProductModel> getAll(String categoryPath, BasicDBObject filters, BasicDBObject sort);

    ArrayList<ProductModel> getAll(Bson query, BasicDBObject filters, BasicDBObject sort);

    ProductModel updateOne(String id, ProductModel newProduct);

    Boolean deleteOne(String id);

    ProductModel getVariant(String id);

}
