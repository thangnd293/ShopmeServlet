package com.api.service.bill;

import java.util.ArrayList;

import com.api.model.bill.BillModel;

import org.bson.types.ObjectId;

public interface IBillService {
    public BillModel addBill(BillModel bill) throws Exception;

    public ArrayList<BillModel> getAll();

    public ArrayList<BillModel> getAll(ObjectId userId) throws Exception;
}
