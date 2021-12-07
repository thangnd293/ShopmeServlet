package com.api.model.dashboard;

public class ChartUserModel {
    private String name;
    private String currMonth;
    private int[] values;

    public ChartUserModel (String name, String currMonth, int[] values) {
        this.name = name;
        this.currMonth = currMonth;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrMonth() {
        return currMonth;
    }

    public void setCurrMonth(String currMonth) {
        this.currMonth = currMonth;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

}
