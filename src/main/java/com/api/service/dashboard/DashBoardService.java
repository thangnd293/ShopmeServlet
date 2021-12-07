package com.api.service.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;

import com.api.dao.dashBoard.DashBoardDAO;
import com.api.model.bill.BillModel;
import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;
import com.api.model.dashboard.DashBoardModel;
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
        BasicDBObject matchCurrMonth = new BasicDBObject("$match",
                new BasicDBObject("createAt", new BasicDBObject("$gt", startTimeToday).append("$lt", endTimeToday)));

        BasicDBObject matchPrevMonth = new BasicDBObject("$match",
                new BasicDBObject("createAt",
                        new BasicDBObject("$gt", startTimeEarlier).append("$lt", endTimeEarlier)));
        DashBoardDAO dashBoardDAO = new DashBoardDAO();

        String temp = earlier.getMonth().toString();
        String prevMonth = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        CardRevenue cardRevenue = dashBoardDAO.getRevenue(matchCurrMonth, matchPrevMonth);
        cardRevenue.setPrevMonth(prevMonth);

        CardAmountSold cardAmountSold = dashBoardDAO.getAmountSold(matchCurrMonth, matchPrevMonth);
        cardAmountSold.setPrevMonth(prevMonth);

        CardCountNewUser cardCountNewUser = dashBoardDAO.getCountNewUser(matchCurrMonth, matchPrevMonth);
        cardCountNewUser.setPrevMonth(prevMonth);

        return null;
    }

}
