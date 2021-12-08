package com.api.dao.bill;

import java.util.ArrayList;

import com.api.model.bill.BillModel;

import org.bson.types.ObjectId;

public interface IBillDAO {
    public BillModel addOne(BillModel bill); 

    public ArrayList<BillModel> getAll();

    public ArrayList<BillModel> getAll(ObjectId userId);

    public ArrayList<BillModel> getBills(int limit);

}
