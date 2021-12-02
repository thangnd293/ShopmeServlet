package com.api.model.category;

import java.util.ArrayList;

public class CategoryBuilder implements ICategoryBuilder {
    private String id;
    private String name;
    private String parent;
    private String path;
    private int level;
    private String slug;
    private ArrayList<CategoryModel> children;

    @Override
    public CategoryBuilder addId(String id) {
        this.id = id;
        return this;
    }

    @Override

    public CategoryBuilder addLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public CategoryBuilder addName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CategoryBuilder addParent(String parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public CategoryBuilder addPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public CategoryBuilder addSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @Override
    public CategoryBuilder addChildren(ArrayList<CategoryModel> children) {
        this.children = children;
        return this;
    }

    @Override
    public CategoryModel build() {
        return new CategoryModel(id, name, parent, path, level, slug, children);
    }

}
