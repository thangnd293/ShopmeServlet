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
        BasicDBObject sortBson = new BasicDBObject("createAt", -1);
        MongoCursor<BillModel> cursor = billCollection.find().sort(sortBson).iterator();

        while (cursor.hasNext()) {
            BillModel bill = cursor.next();
            bills.add(bill);
        }
        return bills;
    }

    @Override
    public ArrayList<BillModel> getAll(ObjectId userId) {
        ArrayList<BillModel> bills = new ArrayList<BillModel>();
        BasicDBObject sortBson = new BasicDBObject("createAt", -1);

        MongoCursor<BillModel> cursor = billCollection.find(eq("user", userId)).sort(sortBson).iterator();

        while (cursor.hasNext()) {
            BillModel bill = cursor.next();
            bills.add(bill);
        }
        return bills;
    }

    @Override
    public ArrayList<BillModel> getBills(int limit) {
        ArrayList<BillModel> bills = new ArrayList<BillModel>();
        BasicDBObject sortBson = new BasicDBObject("createAt", -1);
        
        MongoCursor<BillModel> cursor = billCollection.find().sort(sortBson).limit(limit).iterator();

        while (cursor.hasNext()) {
            BillModel bill = cursor.next();
            bills.add(bill);
        }
        
        return bills;
    }
}
