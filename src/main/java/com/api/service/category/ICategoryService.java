package com.api.service.category;

import java.util.ArrayList;

import com.api.model.category.CategoryModel;

public interface ICategoryService {

    CategoryModel addCategory(CategoryModel category);

    CategoryModel getCategory(String id) throws Exception; 

    ArrayList<CategoryModel> getSubCategoriesByParent(String parent);

    ArrayList<CategoryModel> getAllCategories();
    
}
