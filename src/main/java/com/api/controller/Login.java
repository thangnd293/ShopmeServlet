package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.helper.TokenJwt;
import com.api.helper.returnClass.JsonAuth;
import com.api.model.user.UserMapping;
import com.api.model.user.UserModel;
import com.api.service.auth.AuthService;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/login")
public class Login extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    try {
      JsonObject data = HandleData.dataToJson(req);
      UserModel userLogin = UserMapping.map(data);
      AuthService userService = new AuthService();

      UserModel user = userService.login(userLogin);

      String token = TokenJwt.generateJwt(user);
      JsonAuth result = new JsonAuth(token, user);
      String json = result.toString();
      printJson(json, 200, resp);
    } catch (Exception e) {
      printJsonError("fail", e.getMessage(), 404, resp);
    }
  }
}
