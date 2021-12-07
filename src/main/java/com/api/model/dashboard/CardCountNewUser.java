package com.api.model.dashboard;

public class CardCountNewUser extends CardModel {
    private int count;

    public CardCountNewUser(int count, double growthRate, String prevMonth) {
        super(growthRate, prevMonth);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
