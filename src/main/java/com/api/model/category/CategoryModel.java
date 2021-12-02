package com.api.model.category;

import java.util.ArrayList;

public class CategoryModel {
    private String id;
    private String name;
    private String parent;
    private String path;
    private int level;
    private String slug;
    private ArrayList<CategoryModel> children;

    public CategoryModel(String id, String name, String parent, String path, int level, String slug, ArrayList<CategoryModel> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.path = path;
        this.level = level;
        this.slug = slug;
        this.children = children;
    }

    public CategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<CategoryModel> getChildren() {
        return this.children;
    }

    public void setChildren(ArrayList<CategoryModel> children) {
        this.children = children;
    }

}
