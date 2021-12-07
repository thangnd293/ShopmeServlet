package com.api.model.filter;

import java.util.ArrayList;

public class FilterModel {
    private String id;
    private String type;
    private String name;
    private ArrayList<FilterModel> children;
    public FilterModel(String id, String type, String name, ArrayList<FilterModel> children) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.children = children;
    }

    public FilterModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return ArrayList<FilterModel> return the children
     */
    public ArrayList<FilterModel> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<FilterModel> children) {
        this.children = children;
    }

}
