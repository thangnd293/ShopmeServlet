package com.api.model.dashboard;

public class ChartSaleModel {
    private String name;
    private String[] categories;
    private double[] values;

    public ChartSaleModel(String name, String[] categories, double[] values) {
        this.name = name;
        this. categories = categories;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

}
