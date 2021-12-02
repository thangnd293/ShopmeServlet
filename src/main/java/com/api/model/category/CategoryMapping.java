package com.api.model.category;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class CategoryMapping implements IMapping<CategoryModel> {
    public static final CategoryModel map(JsonObject object) {
        String id = object.get("id").toString();
        String name = object.get("name").toString();
        String parent = object.get("parent").toString();
        String path = object.get("path").toString();
        int level = Integer.parseInt(object.get("level").toString());
        String slug = object.get("slug").toString();

        return new CategoryBuilder()
                .addId(id)
                .addName(name)
                .addParent(parent)
                .addPath(path)
                .addLevel(level)
                .addSlug(slug)
                .build();
    }
}
