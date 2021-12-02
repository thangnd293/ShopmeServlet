package com.api.dao.category;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.api.config.database.DatabaseConnect;
import com.api.model.category.CategoryModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

public class CategoryDAO implements ICategoryDAO {
    private MongoCollection<CategoryModel> categoryCollection = new DatabaseConnect().getCollection("category", CategoryModel.class);

    @Override
    public CategoryModel addCategory(CategoryModel category) {
        categoryCollection.insertOne(category);
        return category;
    }

    @Override
    public CategoryModel getOne(String id){
        CategoryModel category = categoryCollection.find(Filters.eq("_id", id)).first();      
        return category;
    }

    private Bson eq(String string, String id) {
        return null;
    }

    @Override
    public ArrayList<CategoryModel> getSubCategoriesByParent(String parent) {
        ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();

        MongoCursor<CategoryModel> cursor = categoryCollection.find(eq("parent", parent)).iterator();

        while(cursor.hasNext()) {
            categoryList.add(cursor.next());
        }

        return categoryList;
    }

    @Override
    public ArrayList<CategoryModel> getAllCategories() {
        ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();

        Consumer<CategoryModel> addCategory = new Consumer<CategoryModel>() {
            @Override
            public void accept(final CategoryModel category) {
                categoryList.add(category);
            }
        };

        categoryCollection.find().forEach(addCategory);

        return categoryList;
    }
}
