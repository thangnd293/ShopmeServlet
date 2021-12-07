package com.api.config.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.api.model.cart.CartModel;
import com.api.model.category.CategoryModel;
import com.api.model.filter.FilterModel;
import com.api.model.product.ProductModel;
import com.api.model.user.UserModel;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.concurrent.TimeUnit;


public class DatabaseConnect implements IDatabase {
  private MongoClient mongoClient;
  private CodecRegistry pojoCodecRegistry;
  private MongoDatabase dataBase;

  public DatabaseConnect() {
    mongoClient = MongoClients.create(
    "mongodb+srv://thangnd:kVCjpMjwvKd5vzFS@shopme.e2nl2.mongodb.net/shopme?retryWrites=true&w=majority");

    // mongoClient = MongoClients.create();
    pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    dataBase = mongoClient.getDatabase("shopme").withCodecRegistry(pojoCodecRegistry);

    MongoCollection<CategoryModel> Category = dataBase.getCollection("category", CategoryModel.class);
    Category.createIndex(Indexes.ascending("parent"));
    Category.createIndex(Indexes.ascending("path"));

    MongoCollection<FilterModel> Filter = dataBase.getCollection("filter", FilterModel.class);
    Filter.createIndex(Indexes.ascending("type"));

    MongoCollection<UserModel> User = dataBase.getCollection("user", UserModel.class);

    User.createIndex(Indexes.ascending("verifyExpireAt"),
    new IndexOptions().expireAfter(100L, TimeUnit.MINUTES));

    MongoCollection<CartModel> Cart = dataBase.getCollection("cart", CartModel.class);
    Cart.createIndex(Indexes.ascending("user"));

    MongoCollection<ProductModel> Product = dataBase.getCollection("product", ProductModel.class);

    Product.createIndex(Indexes.ascending("facets"));
    Product.createIndex(Indexes.ascending("categoryPath"));
  }

  public <Model> MongoCollection<Model> getCollection(String name, Class<Model> type) {
    return dataBase.getCollection(name, type);
  }
  
}
