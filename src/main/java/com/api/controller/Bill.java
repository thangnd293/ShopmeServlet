package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import com.api.helper.returnClass.JsonMany;
import com.api.helper.returnClass.JsonOne;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.bill.BillMapping;
import com.api.model.bill.BillModel;
import com.api.service.bill.BillService;
import com.google.gson.JsonObject;
import org.bson.types.ObjectId;

@WebServlet(urlPatterns = "/api/v1/bill/*")
public class Bill extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            BillService billService = new BillService();
            ArrayList<BillModel> bills = billService.getAll();
            JsonMany<BillModel> result = new JsonMany<BillModel>(bills.size(), bills);

            String json = result.toString();
            printJson(json, 200, resp);
        } else {
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && pathParts[1].equals("my-bill")) {
                try {
                    String userId = (String) req.getAttribute("userId");
                    BillService billService = new BillService();
                    ArrayList<BillModel> bills = billService.getAll(new ObjectId(userId));
                    JsonMany<BillModel> result = new JsonMany<BillModel>(bills.size(), bills);

                    String json = result.toString();
                    printJson(json, 200, resp);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 404, resp);
                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            JsonObject data = HandleData.dataToJson(req);
            BillModel bill = BillMapping.map(data);
            String userId = (String) req.getAttribute("userId");

            bill.setUser(new ObjectId(userId));

            BillService billService = new BillService();
            bill = billService.addBill(bill);

            JsonOne<BillModel> result = new JsonOne<BillModel>(bill);

            String json = result.toString();
            printJson(json, 200, resp);

        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 404, resp);
        }
    }
}
