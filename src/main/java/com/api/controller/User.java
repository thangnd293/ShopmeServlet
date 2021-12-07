package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.api.helper.HandleJson.printJson;
import com.api.helper.returnClass.JsonMany;
import com.api.model.user.UserModel;
import com.api.service.user.UserService;


@WebServlet(urlPatterns = "/api/v1/user")
public class User extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    UserService userService = new UserService();
    ArrayList<UserModel> users = userService.getAllUser();
    JsonMany<UserModel> result = new JsonMany<UserModel>(users.size(), users);

    String json = result.toString();
    
    printJson(json, 200, resp);
  }
}
