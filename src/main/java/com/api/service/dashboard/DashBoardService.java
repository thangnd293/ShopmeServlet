package com.api.service.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;

import com.api.dao.bill.BillDAO;
import com.api.dao.dashBoard.DashBoardDAO;
import com.api.dao.user.UserDAO;
import com.api.model.bill.BillModel;
import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;
import com.api.model.dashboard.ChartSaleModel;
import com.api.model.dashboard.ChartUserModel;
import com.api.model.dashboard.DashBoardModel;
import com.api.model.user.UserModel;
import com.mongodb.BasicDBObject;

public class DashBoardService implements IDashBoardService {

    @Override
    public DashBoardModel getDashBoard() {

        LocalDate today = LocalDate.now();
        LocalDate earlier = today.minusMonths(1);
        LocalDate startTimeToday = today.withDayOfMonth(1);
        LocalDate endTimeToday = today.withDayOfMonth(today.lengthOfMonth());

        LocalDate startTimeEarlier = earlier.withDayOfMonth(1);
        LocalDate endTimeEarlier = earlier.withDayOfMonth(earlier.lengthOfMonth());

        BasicDBObject matchCurrMonth = getMatchInAMonth(today);
        BasicDBObject matchPrevMonth = getMatchInAMonth(earlier);

        DashBoardDAO dashBoardDAO = new DashBoardDAO();

        String prevMonthStr = getMonth(earlier);
        String currMonthStr = getMonth(today);

        CardRevenue cardRevenue = dashBoardDAO.getRevenue(matchCurrMonth, matchPrevMonth, prevMonthStr);

        CardAmountSold cardAmountSold = dashBoardDAO.getAmountSold(matchCurrMonth, matchPrevMonth, prevMonthStr);

        CardCountNewUser cardCountNewUser = dashBoardDAO.getCountNewUser(startTimeToday, endTimeToday, startTimeEarlier, endTimeEarlier, prevMonthStr);

        ChartSaleModel chartSale = dashBoardDAO.getChartSale(startTimeToday, endTimeToday, currMonthStr);

        ChartUserModel chartNewUser = dashBoardDAO.getChartUser(startTimeToday, endTimeToday, currMonthStr);
        
        ArrayList<UserModel> newUsers = getNewUsers();

        ArrayList<BillModel> newBills = getNewBills();

        DashBoardModel dashBoard = new DashBoardModel(cardRevenue, cardAmountSold, cardCountNewUser, chartSale, chartNewUser, newBills, newUsers);
        return dashBoard;
    }

    private ArrayList<UserModel> getNewUsers() {
        UserDAO userDAO = new UserDAO();
        ArrayList<UserModel> users = userDAO.getUsers(10);
        for (UserModel user : users) {
            user.set_id(user.getId().toString());
            user.setPassword(null);
            user.setId(null);
            user.setRole(null);
            user.setChangePasswordAt(null);
        }
        return users;
    }

    private ArrayList<BillModel> getNewBills() {
        BillDAO billDAO = new BillDAO();
        ArrayList<BillModel> bills = billDAO.getBills(10);

        for (BillModel bill : bills) {
            bill.set_id(bill.getId().toString());
            bill.setId(null);
        }

        return bills;
    }

    private BasicDBObject getMatchInAMonth(LocalDate date) {
        LocalDate startTime = date.withDayOfMonth(1);
        LocalDate endTime = date.withDayOfMonth(date.lengthOfMonth());

        BasicDBObject match = new BasicDBObject("$match",
                new BasicDBObject("createAt", new BasicDBObject("$gt", startTime).append("$lt", endTime)));

        return match;
    }

    private String getMonth(LocalDate date) {
        String temp = date.getMonth().toString();
        String monthName = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();

        return monthName;
    }
}
