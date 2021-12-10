package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleData;
import com.api.helper.returnClass.JsonMany;
import com.api.helper.returnClass.JsonOne;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.bill.BillMapping;
import com.api.model.bill.BillModel;
import com.api.model.user.UserModel;
import com.api.service.bill.BillService;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/bill/*")
public class Bill extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        // api/v1/bill
        // Admin lay tat ca bill
        if (pathInfo == null) {
            this.adminGetAllBill(req, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            // api/v1/bill/my-bill
            // User lay bill cua user
            if(pathParts.length == 1 && pathParts[1].equals("my-bill")) {
                this.userGetUsersBill(req, resp);
            } else {
                printJsonError("fail", "Not found", 404, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {

            UserModel user = (UserModel) req.getAttribute("user");

            if (!Check.isUser(user)) {
                throw new Exception("You do not have permission");
            }

            JsonObject data = HandleData.dataToJson(req);
            BillModel bill = BillMapping.map(data);

            bill.setUser(user.getId());

            BillService billService = new BillService();
            bill = billService.addBill(bill);

            JsonOne<BillModel> result = new JsonOne<BillModel>(bill);

            String json = result.toString();
            printJson(json, 200, resp);

        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    private void adminGetAllBill(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            UserModel user = (UserModel) req.getAttribute("user");

            if (!Check.isAdmin(user)) {
                throw new Exception("You do not have permission");
            }

            BillService billService = new BillService();
            ArrayList<BillModel> bills = billService.getAll();
            JsonMany<BillModel> result = new JsonMany<BillModel>(bills.size(), bills);

            String json = result.toString();
            printJson(json, 200, resp);
        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    private void userGetUsersBill(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            UserModel user = (UserModel) req.getAttribute("user");
            BillService billService = new BillService();
            ArrayList<BillModel> bills = billService.getAll(user.getId());
            JsonMany<BillModel> result = new JsonMany<BillModel>(bills.size(), bills);

            String json = result.toString();
            printJson(json, 200, resp);
        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 404, resp);
        }
    }
}
