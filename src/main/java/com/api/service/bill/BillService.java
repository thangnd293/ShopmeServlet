package com.api.service.bill;

import java.util.ArrayList;

import com.api.dao.bill.BillDAO;
import com.api.model.bill.BillModel;

import org.bson.types.ObjectId;

public class BillService implements IBillService {

    @Override
    public BillModel addBill(BillModel bill) throws Exception {
        BillDAO billDAO = new BillDAO();
        BillModel newBill = billDAO.addOne(bill);
        if(newBill == null) {
            throw new Exception("There was an error during initialization. Please try again later!!");
        }
        return newBill;
    }

    @Override
    public ArrayList<BillModel> getAll() {
        BillDAO billDAO = new BillDAO();

        ArrayList<BillModel> bills = billDAO.getAll();
        return bills;
    }

    @Override
    public ArrayList<BillModel> getAll(ObjectId userId) throws Exception {
        BillDAO billDAO = new BillDAO();

        if(userId.toString().equals("")) {
            throw new Exception("User does not exists");
        }

        ArrayList<BillModel> bills = billDAO.getAll(userId);
        return bills;
    }
    
}
