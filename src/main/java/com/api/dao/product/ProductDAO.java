package com.api.dao.product;

import java.util.ArrayList;
import java.util.Arrays;


import com.api.config.database.DatabaseConnect;
import com.api.model.product.ProductModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;


import static com.mongodb.client.model.Filters.eq;

public class ProductDAO implements IProductDAO {
    private MongoCollection<ProductModel> productCollection = new DatabaseConnect().getCollection("product",
            ProductModel.class);

    @Override
    public ArrayList<ProductModel> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<ProductModel> getAll(ArrayList<String> fieldLimits) {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();
        BasicDBObject fields = new BasicDBObject();

        for (String f : fieldLimits) {
            fields.put(f, 1);
        }

        MongoCursor<ProductModel> cursor = productCollection.find().projection(fields).iterator();
        while (cursor.hasNext()) {
            products.add(cursor.next());
        }
        return products;
    }

    @Override
    public ArrayList<ProductModel> getAll(String categoryPath, BasicDBObject filters, BasicDBObject sort,
            ArrayList<String> fieldLimits) {
        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();

        BasicDBObject fields = new BasicDBObject();

        for (String f : fieldLimits) {
            fields.put(f, 1);
        }

        String queryCategory = String.format("{ \"categoryPath\" : { \"$regex\" : \"%s\"}}", categoryPath);
        BasicDBObject query = BasicDBObject.parse(queryCategory);

        MongoCursor<ProductModel> cursor = productCollection.find(query).filter(filters).sort(sort).projection(fields)
                .iterator();

        while (cursor.hasNext()) {
            productList.add(cursor.next());
        }

        return productList;
    }

    @Override
    public ProductModel getOne(String id) {
        ProductModel product = productCollection.find(eq("_id", id)).first();
        return product;
    }

    @Override
    public ProductModel addOne(ProductModel product) {
        productCollection.insertOne(product);
        return product;
    }

    @Override
    public ProductModel updateOne(String id, ProductModel newProduct) {
        return productCollection.findOneAndReplace(Filters.eq("_id", id), newProduct, new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));
    }

    @Override
    public Boolean deleteOne(String id) {
        return productCollection.deleteOne(eq("_id", id)).getDeletedCount() != 0;
    }

    @Override
    public ProductModel getVariant(String id) {
        ProductModel product = productCollection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("variants._id",  id)))).first();

        if(product == null) return null;

        product.getVariants().removeIf(p -> !p.getId().toString().equals(id));
        return product;
    }

}