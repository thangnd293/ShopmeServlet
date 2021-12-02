package com.api.model.filter;

public class FilterModel {
    private String id;
    private String type;
    private String name;

    public FilterModel(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
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

}
