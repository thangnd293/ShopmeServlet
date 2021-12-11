package com.api.dao.dashBoard;

import java.time.LocalDate;
import java.util.Arrays;

import com.api.config.database.DatabaseConnect;
import com.api.model.dashboard.CardAmountSold;
import com.api.model.dashboard.CardCountNewUser;
import com.api.model.dashboard.CardRevenue;
import com.api.model.dashboard.ChartSaleModel;
import com.api.model.dashboard.ChartUserModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DashBoardDAO implements IDashBoardDAO {
    private MongoCollection<Document> billCollection = new DatabaseConnect().getCollection("bill", Document.class);
    private MongoCollection<Document> userCollection = new DatabaseConnect().getCollection("user", Document.class);

    @Override
    public CardRevenue getRevenue(Bson matchCurrMonth, Bson matchPrevMonth, String prevMonthStr) {
        BasicDBObject groupBy = new BasicDBObject("$group",
                new BasicDBObject("_id", null).append("total", new BasicDBObject("$sum", "$amount")));

        Document currMonth = billCollection.aggregate(Arrays.asList(
                matchCurrMonth,
                groupBy)).first();

        Document prevMonth = billCollection.aggregate(Arrays.asList(
                matchPrevMonth,
                groupBy)).first();

        double quantityCurrMonth = 0;
        if (currMonth != null) {
            quantityCurrMonth = Double.parseDouble(currMonth.get("total").toString());
        }

        double quantityPrevMonth = 0;

        if (prevMonth != null) {
            quantityPrevMonth = Double.parseDouble(prevMonth.get("total").toString());
        }

        double growthRate = 0;

        if (quantityPrevMonth != 0) {
            growthRate = (quantityCurrMonth / quantityPrevMonth) * 100 - 100;
        } else {
            growthRate = 0;
        }

        CardRevenue cardRevenue = new CardRevenue(quantityCurrMonth, growthRate, prevMonthStr);

        return cardRevenue;
    }

    @Override
    public CardAmountSold getAmountSold(Bson matchCurrMonth, Bson matchPrevMonth, String prevMonthStr) {
        BasicDBObject groupBy = new BasicDBObject("$group",
                new BasicDBObject("_id", null).append("quantity", new BasicDBObject("$sum", "$quantity")));

        Document currMonth = billCollection.aggregate(Arrays.asList(
                matchCurrMonth,
                groupBy)).first();

        Document prevMonth = billCollection.aggregate(Arrays.asList(
                matchPrevMonth,
                groupBy)).first();

        int quantityCurrMonth = 0;
        if (currMonth != null) {
            quantityCurrMonth = Integer.parseInt(currMonth.get("quantity").toString());
        }

        int quantityPrevMonth = 0;

        if (prevMonth != null) {
            quantityPrevMonth = Integer.parseInt(prevMonth.get("quantity").toString());
        }

        double growthRate = 0;

        if (quantityPrevMonth != 0) {
            growthRate = (quantityCurrMonth / quantityPrevMonth) * 100 - 100;
        } else {
            growthRate = 0;
        }

        CardAmountSold cardAmountSold = new CardAmountSold(quantityCurrMonth, growthRate, prevMonthStr);

        return cardAmountSold;
    }

    @Override
    public CardCountNewUser getCountNewUser (LocalDate startTimeToday ,LocalDate endTimeToday, LocalDate startTimeEarlier, LocalDate endTimeEarlier, String prevMonthStr) {

        Document currMonth = userCollection.aggregate(Arrays.asList(new Document("$match", 
        new Document("$and", Arrays.asList(new Document("isVerify", true), 
                    new Document("createAt", 
                    new Document("$gt", startTimeToday)
                            .append("$lte", endTimeToday))))), 
        new Document("$group", 
        new Document("_id", 
        new BsonNull())
                .append("count", 
        new Document("$sum", 1L))))).first();

        Document prevMonth = userCollection.aggregate(Arrays.asList(new Document("$match", 
        new Document("$and", Arrays.asList(new Document("isVerify", true), 
                    new Document("createAt", 
                    new Document("$gt", startTimeEarlier)
                            .append("$lte", endTimeEarlier))))), 
        new Document("$group", 
        new Document("_id", 
        new BsonNull())
                .append("count", 
        new Document("$sum", 1L))))).first();

        int countCurrMonth = 0;
        if (currMonth != null) {
            countCurrMonth = Integer.parseInt(currMonth.get("count").toString());
        }

        int countPrevMonth = 0;

        if (prevMonth != null) {
            countPrevMonth = Integer.parseInt(prevMonth.get("count").toString());
        }

        double growthRate = 0;

        if (countPrevMonth != 0) {
            growthRate = (countCurrMonth / countPrevMonth) * 100 - 100;
        } else {
            growthRate = 0;
        }

        CardCountNewUser cardCountNewUser = new CardCountNewUser(countCurrMonth, growthRate, prevMonthStr);

        return cardCountNewUser;
    }

    @Override
    public ChartSaleModel getChartSale(LocalDate startTimeToday, LocalDate endTimeToday, String currMonthStr) {
        MongoCursor<Document> cursor = billCollection.aggregate(Arrays.asList(
            new Document("$match",
            new Document("createAt",
            new Document("$gt", startTimeToday)
            .append("$lte", endTimeToday))),
            new Document("$group",
            new Document("_id",
            new Document("date",
            new Document("$dayOfMonth", "$createAt")))
            .append("total",
            new Document("$sum", "$amount")))))
            .iterator();

        int numberOfDays = endTimeToday.getDayOfMonth();

        String[] categories = new String[numberOfDays];
        for (int i = 0; i < numberOfDays; i++) {
            categories[i] = String.format("%d %s", i + 1, currMonthStr);
        }

        double[] values = new double[numberOfDays];

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Document data = (Document) doc.get("_id");
            int date = Integer.parseInt(data.get("date").toString());
            double value = Double.parseDouble(doc.get("total").toString());
            values[date - 1] = value;
        }

        return new ChartSaleModel("Sales chart", categories, values);
    }

    @Override
    public ChartUserModel getChartUser(LocalDate startTimeToday, LocalDate endTimeToday, String currMonthStr) {
        MongoCursor<Document> cursor = userCollection.aggregate(Arrays.asList(
        new Document("$match", 
        new Document("$and", Arrays.asList(new Document("isVerify", true), 
                    new Document("createAt", 
                    new Document("$gt", startTimeToday)
                            .append("$lte", endTimeToday))))), 
        new Document("$group", 
        new Document("_id", 
        new Document("date", 
        new Document("$dayOfMonth", "$createAt")))
                .append("count", 
        new Document("$sum", 1L)))))
            .iterator();

        int numberOfDays = endTimeToday.getDayOfMonth();

        int[] values = new int[numberOfDays];

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Document data = (Document) doc.get("_id");
            int date = Integer.parseInt(data.get("date").toString());
            int value = Integer.parseInt(doc.get("count").toString());
            values[date - 1] = value;
        }

        return new ChartUserModel("New user chart", currMonthStr, values);
    }

}
