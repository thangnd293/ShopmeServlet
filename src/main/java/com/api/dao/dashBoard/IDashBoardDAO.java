package com.api.dao.dashBoard;


import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;

import org.bson.conversions.Bson;

public interface IDashBoardDAO {
    public CardRevenue getRevenue(Bson matchCurrMonth, Bson matchPrevMonth);

    public CardAmountSold getAmountSold (Bson matchCurrMonth, Bson matchPrevMonth);

    public CardCountNewUser getCountNewUser (Bson matchCurrMonth, Bson matchPrevMonth);

}
