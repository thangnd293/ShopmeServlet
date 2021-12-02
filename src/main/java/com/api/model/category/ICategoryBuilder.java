package com.api.model.category;

import java.util.ArrayList;

public interface ICategoryBuilder {
   CategoryBuilder addId(String id);
   
   CategoryBuilder addName(String name);

   CategoryBuilder addParent(String parent);

   CategoryBuilder addPath(String path);

   CategoryBuilder addLevel(int level);

   CategoryBuilder addSlug(String slug);

    CategoryBuilder addChildren(ArrayList<CategoryModel> children);
    
   CategoryModel build();
}
