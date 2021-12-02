package com.api.dao.category;

import java.util.ArrayList;

import com.api.model.category.CategoryModel;

public interface ICategoryDAO {
    CategoryModel addCategory(CategoryModel category);

    CategoryModel getOne(String id);

    ArrayList<CategoryModel> getSubCategoriesByParent(String parent);

    ArrayList<CategoryModel> getAllCategories();
}
