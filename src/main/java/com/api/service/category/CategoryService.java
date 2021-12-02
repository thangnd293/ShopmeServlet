package com.api.service.category;

import java.util.ArrayList;

import com.api.dao.category.CategoryDAO;
import com.api.model.category.CategoryModel;

public class CategoryService implements ICategoryService {

    @Override
    public CategoryModel addCategory(CategoryModel category) {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.addCategory(category);
    }

    @Override
    public CategoryModel getCategory(String id) throws Exception {
        CategoryDAO categoryDAO = new CategoryDAO();
        CategoryModel category = categoryDAO.getOne(id);
        if(category == null) {
            throw new Exception("Invalid category!!");
        }
        return category;
    }

    @Override
    public ArrayList<CategoryModel> getAllCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<CategoryModel> level1 = new ArrayList<CategoryModel>();
        ArrayList<CategoryModel> level2 = new ArrayList<CategoryModel>();

        ArrayList<CategoryModel> level3 = categoryDAO.getAllCategories();

        int n = level3.size();

        for (int i = 0; i < n; i++) {
            CategoryModel category = level3.get(i);
            if (category.getLevel() == 1) {
                level1.add(category);
                level3.remove(i);
                i--;
                n--;
            } else if (category.getLevel() == 2) {
                level2.add(category);
                level3.remove(i);
                i--;
                n--;
            }
        }

        int m = level2.size();
        for (int i = 0; i < m; i++) {
            CategoryModel parent = level2.get(i);
            parent.setChildren(new ArrayList<CategoryModel>());
            for (int j = 0; j < n; j++) {
                CategoryModel child = level3.get(j);
                if (child.getParent().equals(parent.getPath())) {
                    parent.getChildren().add(child);
                    level3.remove(j);
                    j--;
                    n--;
                }
            }
        }

        for (CategoryModel parent : level1) {
            parent.setChildren(new ArrayList<CategoryModel>());
            for (int i = 0; i < m; i++) {
                CategoryModel child = level2.get(i);
                if (child.getParent().equals(parent.getPath())) {
                    parent.getChildren().add(child);
                    level2.remove(i);
                    i--;
                    m--;
                }
            }
        }
        return level1;
    }

    @Override
    public ArrayList<CategoryModel> getSubCategoriesByParent(String parent) {
        CategoryDAO categoryDAO = new CategoryDAO();

        return categoryDAO.getSubCategoriesByParent(parent);
    }

}
