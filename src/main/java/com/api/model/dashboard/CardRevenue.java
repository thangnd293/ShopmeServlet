package com.api.model.dashboard;

public class CardRevenue extends CardModel {
    private double total;
    public CardRevenue(double total, double growthRate, String prevMonth) {
        super(growthRate, prevMonth);
        this.total = total;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
