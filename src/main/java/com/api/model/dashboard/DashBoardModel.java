package com.api.model.dashboard;

import java.util.ArrayList;

import com.api.model.bill.BillModel;
import com.api.model.user.UserModel;

public class DashBoardModel {
    private CardRevenue revenue;
    private CardAmountSold amountSold;
    private CardCountNewUser countNewUser;
    private ArrayList<BillModel> bills;
    private ArrayList<UserModel> users;

    public DashBoardModel(CardRevenue revenue, CardAmountSold amountSold, CardCountNewUser countNewUser, ArrayList<BillModel> bills, ArrayList<UserModel> users) {
        this.revenue = revenue;
        this.amountSold = amountSold;
        this.countNewUser = countNewUser;
        this.bills = bills;
        this.users = users;
    }

    public CardRevenue getRevenue() {
        return revenue;
    }

    public void setRevenue(CardRevenue revenue) {
        this.revenue = revenue;
    }

    public CardAmountSold getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(CardAmountSold amountSold) {
        this.amountSold = amountSold;
    }

    public CardCountNewUser getCountNewUser() {
        return countNewUser;
    }

    public void setCountNewUser(CardCountNewUser countNewUser) {
        this.countNewUser = countNewUser;
    }

    public ArrayList<BillModel> getBills() {
        return bills;
    }

    public void setBills(ArrayList<BillModel> bills) {
        this.bills = bills;
    }

    public ArrayList<UserModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserModel> users) {
        this.users = users;
    }

}
