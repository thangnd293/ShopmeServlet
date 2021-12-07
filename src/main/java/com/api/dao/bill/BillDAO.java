package com.api.dao.bill;

import java.util.ArrayList;

import com.api.config.database.DatabaseConnect;
import com.api.model.bill.BillModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.eq;

import org.bson.types.ObjectId;

public class BillDAO implements IBillDAO {
    private MongoCollection<BillModel> billCollection = new DatabaseConnect().getCollection("bill", BillModel.class);

    @Override
    public BillModel addOne(BillModel bill) {
        billCollection.insertOne(bill);
        return bill;
    }

    @Override
    public ArrayList<BillModel> getAll() {
        ArrayList<BillModel> bills = new ArrayList<BillModel>();
        BasicDBObject sortObject = BasicDBObject.parse("{ \"createAt\" : 1 }");
        MongoCursor<BillModel> cursor = billCollection.find().sort(sortObject).iterator();

        while (cursor.hasNext()) {
            BillModel bill = cursor.next();
            bills.add(bill);
        }
        return bills;
    }

    @Override
    public ArrayList<BillModel> getAll(ObjectId userId) {
        ArrayList<BillModel> bills = new ArrayList<BillModel>();
        BasicDBObject sortObject = BasicDBObject.parse("{ \"createAt\" : -1 }");
        MongoCursor<BillModel> cursor = billCollection.find(eq("user", userId)).sort(sortObject).iterator();

        while (cursor.hasNext()) {
            BillModel bill = cursor.next();
            bills.add(bill);
        }
        return bills;
    }

    // @Override
    // public ArrayList<BillModel> getAll(Bson query) {
    //     LocalDate today = LocalDate.now();
    //     LocalDate earlier = today.minusMonths(1);
    //     LocalDate startTimeToday = today.withDayOfMonth(1);
    //     LocalDate endTimeToday = today.withDayOfMonth(today.lengthOfMonth());
    //     LocalDate startTimeEarlier = earlier.withDayOfMonth(1);
    //     LocalDate endTimeEarlier = earlier.withDayOfMonth(earlier.lengthOfMonth());
    //     ArrayList<BillModel> bills = new ArrayList<BillModel>();
    //     BasicDBObject matchCurrMonth = new BasicDBObject("$match",
    //             new BasicDBObject("createAt", new BasicDBObject("$gt", startTimeToday).append("$lt", endTimeToday)));

    //     BasicDBObject matchPrevMonth = new BasicDBObject("$match",
    //             new BasicDBObject("createAt", new BasicDBObject("$gt", startTimeEarlier).append("$lt", endTimeEarlier)));

    //     BasicDBObject groupBy = new BasicDBObject("$group",
    //             new BasicDBObject("_id", null).append("total", new BasicDBObject("$sum", "$amount")).append("count",
    //                     new BasicDBObject("$sum", 1)));

    //     MongoCollection<Document> DocCollection = new DatabaseConnect().getCollection("bill", Document.class);

    //     Consumer<Document> print = new Consumer<Document>() {
    //         @Override
    //         public void accept(final Document doc) {
    //             System.out.println(doc.toJson());
    //         }
    //     };

    //     DocCollection.aggregate(Arrays.asList(
    //             matchCurrMonth,
    //             groupBy)).forEach(print);

    //     DocCollection.aggregate(Arrays.asList(
    //                 matchPrevMonth,
    //                 groupBy)).forEach(print);
    

    //     // ArrayList<Document> result = new ArrayList<Document>();
    //     // try {
    //     // for (Document document : output) {
    //     // System.out.println(document);
    //     // }
    //     // } catch (Exception e) {
    //     // System.out.println(e.getMessage());
    //     // }

    //     return bills;
    // }
}
