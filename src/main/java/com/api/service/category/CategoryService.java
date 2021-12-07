package com.api.service.category;

import java.util.ArrayList;

import com.api.dao.category.CategoryDAO;
import com.api.model.category.CategoryModel;

public class CategoryService implements ICategoryService {
    @Override
    public CategoryModel getCategory(String id) throws Exception {
        CategoryDAO categoryDAO = new CategoryDAO();
        CategoryModel category = categoryDAO.getOne(id);
        if(category == null) {
            throw new Exception("Category does not exist!!");
        }
        return category;
    }

    @Override
    public ArrayList<CategoryModel> getAllCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<CategoryModel> categoryLV1 = new ArrayList<CategoryModel>();
        ArrayList<CategoryModel> categoryLV2 = new ArrayList<CategoryModel>();
        ArrayList<CategoryModel> categoryLV3 = categoryDAO.getAll();

        int n = categoryLV3.size();

        for (int i = 0; i < n; i++) {
            CategoryModel category = categoryLV3.get(i);
            if (category.getLevel() == 1) {
                categoryLV1.add(category);
                categoryLV3.remove(i);
                i--;
                n--;
            } else if (category.getLevel() == 2) {
                categoryLV2.add(category);
                categoryLV3.remove(i);
                i--;
                n--;
            }
        }

        int m = categoryLV2.size();
        for (int i = 0; i < m; i++) {
            CategoryModel parent = categoryLV2.get(i);
            parent.setChildren(new ArrayList<CategoryModel>());
            for (int j = 0; j < n; j++) {
                CategoryModel child = categoryLV3.get(j);
                if (child.getParent().equals(parent.getPath())) {
                    parent.getChildren().add(child);
                    categoryLV3.remove(j);
                    j--;
                    n--;
                }
            }
        }

        for (CategoryModel parent : categoryLV1) {
            parent.setChildren(new ArrayList<CategoryModel>());
            for (int i = 0; i < m; i++) {
                CategoryModel child = categoryLV2.get(i);
                if (child.getParent().equals(parent.getPath())) {
                    parent.getChildren().add(child);
                    categoryLV2.remove(i);
                    i--;
                    m--;
                }
            }
        }
        return categoryLV1;
    }

    // @Override
    // public ArrayList<CategoryModel> getSubCategoriesByParent(String parent) {
    //     CategoryDAO categoryDAO = new CategoryDAO();

    //     return categoryDAO.getAll(parent);
    // }

}
