package com.api.dao.dashBoard;

import java.util.Arrays;

import com.api.config.database.DatabaseConnect;
import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;

public class DashBoardDAO implements IDashBoardDAO {
    private MongoCollection<Document> billCollection = new DatabaseConnect().getCollection("bill", Document.class);
    private MongoCollection<Document> userCollection = new DatabaseConnect().getCollection("user", Document.class);

    @Override
    public CardRevenue getRevenue(Bson matchCurrMonth, Bson matchPrevMonth) {
        BasicDBObject groupBy = new BasicDBObject("$group",
                new BasicDBObject("_id", null).append("total", new BasicDBObject("$sum", "$amount")));

        Document currMonth = billCollection.aggregate(Arrays.asList(
                matchCurrMonth,
                groupBy)).first();

        Document prevMonth = billCollection.aggregate(Arrays.asList(
                matchPrevMonth,
                groupBy)).first();

        double quantityCurrMonth = 0;
        if(currMonth != null) {
            quantityCurrMonth = Double.parseDouble(currMonth.get("total").toString());
        }

        double quantityPrevMonth = 0;

        if(prevMonth != null) {
            quantityPrevMonth = Double.parseDouble(prevMonth.get("total").toString());
        }

        double growthRate = 0;

        if(quantityPrevMonth != 0) {
            growthRate = (quantityCurrMonth / quantityPrevMonth) * 100 - 100; 
        } else {
            growthRate = 0;
        }

        // System.out.println(earlier.getMonth().toString());

        CardRevenue cardRevenue = new CardRevenue(quantityCurrMonth, growthRate, null);

        return cardRevenue;
    }

    @Override
    public CardAmountSold getAmountSold(Bson matchCurrMonth, Bson matchPrevMonth) {
        BasicDBObject groupBy = new BasicDBObject("$group",
                new BasicDBObject("_id", null).append("quantity", new BasicDBObject("$sum", "$quantity")));

        Document currMonth = billCollection.aggregate(Arrays.asList(
                matchCurrMonth,
                groupBy)).first();

        Document prevMonth = billCollection.aggregate(Arrays.asList(
                matchPrevMonth,
                groupBy)).first();

        int quantityCurrMonth = 0;
        if(currMonth != null) {
            quantityCurrMonth = Integer.parseInt(currMonth.get("quantity").toString());
        }

        int quantityPrevMonth = 0;

        if(prevMonth != null) {
            quantityPrevMonth = Integer.parseInt(prevMonth.get("quantity").toString());
        }

        double growthRate = 0;

        if(quantityPrevMonth != 0) {
            growthRate = (quantityCurrMonth / quantityPrevMonth) * 100 - 100; 
        } else {
            growthRate = 0;
        }

        CardAmountSold cardAmountSold = new CardAmountSold(quantityCurrMonth, growthRate, null);

        return cardAmountSold;
    }

    @Override
    public CardCountNewUser getCountNewUser(Bson matchCurrMonth, Bson matchPrevMonth) {
        BasicDBObject groupBy = new BasicDBObject("$group",
                new BasicDBObject("_id", null).append("count", new BasicDBObject("$sum", 1)));

        Document currMonth = userCollection.aggregate(Arrays.asList(
                matchCurrMonth,
                groupBy)).first();

        Document prevMonth = userCollection.aggregate(Arrays.asList(
                matchPrevMonth,
                groupBy)).first();

        int countCurrMonth = 0;
        if(currMonth != null) {
            countCurrMonth = Integer.parseInt(currMonth.get("count").toString());
        }

        int countPrevMonth = 0;

        if(prevMonth != null) {
            countPrevMonth = Integer.parseInt(prevMonth.get("count").toString());
        }

        double growthRate = 0;

        if(countPrevMonth != 0) {
            growthRate = (countCurrMonth / countPrevMonth) * 100 - 100; 
        } else {
            growthRate = 0;
        }

        CardCountNewUser cardCountNewUser = new CardCountNewUser(countCurrMonth, growthRate, null);
        
        return cardCountNewUser;
    }

    

    
}
