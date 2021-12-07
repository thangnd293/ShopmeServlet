package com.api.model.dashboard;

public class CardAmountSold extends CardModel {
    private int amount;
    public CardAmountSold(int amount, double growthRate, String prevMonth) {
        super(growthRate, prevMonth);
        this.amount = amount;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
