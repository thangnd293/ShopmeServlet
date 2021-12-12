package com.api.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleData;
import com.api.helper.returnClass.JsonMany;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.bill.BillMapping;
import com.api.model.bill.BillModel;
import com.api.model.bill.ItemModel;
import com.api.model.user.UserModel;
import com.api.service.bill.BillService;
import com.api.utils.EmailUtil;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/bill/*")
public class Bill extends HttpServlet {

    private String host;
    private String port;
    private String email;
    private String password;

    public void init() {
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        email = context.getInitParameter("email");
        password = context.getInitParameter("password");
    }

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
            if(pathParts.length == 2 && pathParts[1].equals("my-bill")) {
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

            boolean checkIsEmailSend = this.sendEmail(user, bill);

            if (!checkIsEmailSend) {
                throw new Exception("There were an error. Please try again!");
            }

            String json = "{ \"status\": \"success\" , \"message\": \"Thank you, bill has been sent to your email\"}";
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

    private boolean sendEmail(UserModel user, BillModel bill) throws AddressException, MessagingException {
        String subject = "Thank you ^^";
            String path = this.getServletContext().getRealPath("/emailtemplate/transaction.html");
            String pathItem = this.getServletContext().getRealPath("/emailtemplate/item.html");

            String html = EmailUtil.getHtmlEmail(path);
            String htmlItem = EmailUtil.getHtmlEmail(pathItem);
            String htmlListItem = "";
            

            ZoneId zid = ZoneId.of("Asia/Ho_Chi_Minh");  
            String date = LocalDate.now(zid).toString();
            html = html.replace("<%NAME>", user.getFname());
            html = html.replace("<%AMOUNT>", String.valueOf(bill.getAmount()));
            html = html.replace("<%DATE>", date);

            for (ItemModel item : bill.getItems()) {
                String itemTemplate = "";
                itemTemplate = htmlItem.replace("<%SKU>", item.getSku());
                itemTemplate = itemTemplate.replace("<%NAME>", item.getName());
                itemTemplate = itemTemplate.replace("<%QUANTITY>",Integer.toString(item.getQuantity()));
                itemTemplate = itemTemplate.replace("<%TOTAL>", String.valueOf(item.getTotal()));
                htmlListItem += itemTemplate;
            }

            html = html.replace("<%LIST-ITEM>", htmlListItem);

            return EmailUtil.sendEmail(host, port, email, password, user.getEmail(), subject, html);
    }
}
