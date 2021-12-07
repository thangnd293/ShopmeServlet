package com.api.model.dashboard;

public class CardModel {
    private double growthRate;
    private String prevMonth;

    public CardModel(double growthRate, String prevMonth) {
        this.growthRate = growthRate;
        this.prevMonth = prevMonth;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public String getPrevMonth() {
        return prevMonth;
    }

    public void setPrevMonth(String prevMonth) {
        this.prevMonth = prevMonth;
    }

}
