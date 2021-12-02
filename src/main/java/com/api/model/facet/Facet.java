package com.api.model.facet;

import java.util.ArrayList;

import com.api.model.filter.FilterModel;


public class Facet {
    private String name;
    private ArrayList<FilterModel> values;
    public Facet(String name, ArrayList<FilterModel> values) {
        this.name = name;
        this.values = values;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FilterModel> getValues() {
        return values;
    }

    public void setValues(ArrayList<FilterModel> values) {
        this.values = values;
    }

}
