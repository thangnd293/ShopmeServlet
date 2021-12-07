package com.api.dao.category;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.api.config.database.DatabaseConnect;
import com.api.model.category.CategoryModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.eq;


public class CategoryDAO implements ICategoryDAO {
    private MongoCollection<CategoryModel> categoryCollection = new DatabaseConnect().getCollection("category", CategoryModel.class);

    @Override
    public CategoryModel getOne(String id){
        CategoryModel category = categoryCollection.find(eq("_id", id)).first();      
        return category;
    }

    @Override
    public ArrayList<CategoryModel> getAll(String parent) {
        ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();

        MongoCursor<CategoryModel> cursor = categoryCollection.find(eq("parent", parent)).iterator();

        while(cursor.hasNext()) {
            categoryList.add(cursor.next());
        }

        return categoryList;
    }

    @Override
    public ArrayList<CategoryModel> getAll() {
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
