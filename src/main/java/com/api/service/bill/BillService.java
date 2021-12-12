package com.api.service.bill;

import java.util.ArrayList;

import com.api.dao.bill.BillDAO;
import com.api.model.bill.BillModel;
import com.api.service.cart.CartService;

import org.bson.types.ObjectId;

public class BillService implements IBillService {

    @Override
    public BillModel addBill(BillModel bill) throws Exception {
        BillDAO billDAO = new BillDAO();
        BillModel newBill = billDAO.addOne(bill);
        if(newBill == null) {
            throw new Exception("There was an error during initialization. Please try again later!!");
        }

        CartService cartService = new CartService();
        cartService.resetCart(newBill.getUser());
        return newBill;
    }

    @Override
    public ArrayList<BillModel> getAll() {
        BillDAO billDAO = new BillDAO();

        ArrayList<BillModel> bills = billDAO.getAll();
        for (BillModel bill : bills) {
            bill.set_id(bill.getId().toString());
        }
        return bills;
    }

    @Override
    public ArrayList<BillModel> getAll(ObjectId userId) throws Exception {
        BillDAO billDAO = new BillDAO();

        if(userId.toString().equals("")) {
            throw new Exception("User does not exists");
        }

        ArrayList<BillModel> bills = billDAO.getAll(userId);

        for (BillModel bill : bills) {
            bill.set_id(bill.getId().toString());
        }
        return bills;
    }
    
}
