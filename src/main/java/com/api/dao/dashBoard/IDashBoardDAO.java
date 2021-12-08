package com.api.dao.dashBoard;


import java.time.LocalDate;

import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;
import com.api.model.dashboard.ChartSaleModel;
import com.api.model.dashboard.ChartUserModel;

import org.bson.conversions.Bson;

public interface IDashBoardDAO {
    public CardRevenue getRevenue(Bson matchCurrMonth, Bson matchPrevMonth, String prevMonthStr);

    public CardAmountSold getAmountSold (Bson matchCurrMonth, Bson matchPrevMonth, String prevMonthStr);

    public CardCountNewUser getCountNewUser (Bson matchCurrMonth, Bson matchPrevMonth, String prevMonthStr);

    public ChartSaleModel getChartSale (LocalDate startTimeToday, LocalDate endTimeToday, String currMonthStr);

    public ChartUserModel getChartUser(LocalDate startTimeToday, LocalDate endTimeToday, String currMonthStr);

}
