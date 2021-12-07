package com.api.dao.category;

import java.util.ArrayList;

import com.api.model.category.CategoryModel;

public interface ICategoryDAO {
    CategoryModel getOne(String id);

    ArrayList<CategoryModel> getAll(String parent);

    ArrayList<CategoryModel> getAll();
}
