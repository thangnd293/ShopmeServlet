package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;

import com.api.helper.Check;
import com.api.helper.returnClass.JsonOne;
import com.api.model.dashboard.DashBoardModel;
import com.api.model.user.UserModel;
import com.api.service.dashboard.DashBoardService;

@WebServlet(urlPatterns = "/api/v1/dashboard")
public class DashBoard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {

            UserModel user = (UserModel) req.getAttribute("user");

            if (!Check.isAdmin(user)) {
                throw new Exception("You do not have permission");
            }

            DashBoardService dashBoardService = new DashBoardService();
            DashBoardModel dashBoard = dashBoardService.getDashBoard();
            JsonOne<DashBoardModel> result = new JsonOne<DashBoardModel>(dashBoard);

            String json = result.toString();
            printJson(json, 200, resp);

        } catch (Exception e) {
            printJsonError("fail", "Not found", 404, resp);
        }

    }
}
