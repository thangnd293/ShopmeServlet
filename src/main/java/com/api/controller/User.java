package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;

import com.api.helper.Check;
import com.api.helper.HandleData;
import com.api.helper.returnClass.JsonMany;
import com.api.helper.returnClass.JsonOne;
import com.api.model.user.UserModel;
import com.api.service.user.UserService;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/user/*")
public class User extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    String pathInfo = req.getPathInfo();

    if (pathInfo != null) {
      printJsonError("fail", "Not found", 404, resp);
    }
    // api/v1/user
    else {
      try {
        UserModel user = (UserModel) req.getAttribute("user");

        if (!Check.isAdmin(user)) {
          throw new Exception("You do not have permission");
        }

        UserService userService = new UserService();
        ArrayList<UserModel> users = userService.getAllUser();
        JsonMany<UserModel> result = new JsonMany<UserModel>(users.size(), users);

        String json = result.toString();

        printJson(json, 200, resp);
      } catch (Exception e) {
        printJsonError("fail", e.getMessage(), 403, resp);
      }
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    String pathInfo = req.getPathInfo();

    // api/v1/user
    if (pathInfo == null) {
      printJsonError("fail", "Not found", 404, resp);
    }
    // api/v1/user/*
    else {
      String[] pathParts = pathInfo.split("/");
      // api/v1/user/me
      // Cap nhat avt
      if (pathParts.length == 2 && pathParts[1].equals("me")) {
        UserModel user = (UserModel) req.getAttribute("user");

        JsonObject data = HandleData.dataToJson(req);

        String photo = data.get("photo") != null ? data.get("photo").getAsString() : null;

        user.setPhoto(photo);

        UserService userService = new UserService();
        try {
          user = userService.updatePhoto(user.getId(), user);
          JsonOne<UserModel> result = new JsonOne<UserModel>(user);

          String json = result.toString();
          printJson(json, 200, resp);
        } catch (Exception e) {
          printJsonError("fail", e.getMessage(), 404, resp);
        }
      }
      // api/v1/user/update-password
      // Cap nhat mat khau
      else if (pathParts.length == 2 && pathParts[1].equals("update-password")) {
        UserModel user = (UserModel) req.getAttribute("user");

        JsonObject data = HandleData.dataToJson(req);

        String passwordCurrent = data.get("passwordCurrent") != null ? data.get("passwordCurrent").getAsString() : null;
        String password = data.get("password") != null ? data.get("password").getAsString() : null;
        String passwordConfirm = data.get("passwordConfirm") != null ? data.get("passwordConfirm").getAsString() : null;

        UserService userService = new UserService();
        try {
          user = userService.updatePassword(user, passwordCurrent, password, passwordConfirm);
          JsonOne<UserModel> result = new JsonOne<UserModel>(user);

          String json = result.toString();
          printJson(json, 200, resp);
        } catch (Exception e) {
          printJsonError("fail", e.getMessage(), 404, resp);
        }
      }
      // api/v1/user/:id
      // admin cap nhat quyen cho user
      else if (pathParts.length == 2) {
        try {
          UserModel user = (UserModel) req.getAttribute("user");

          if (!Check.isAdmin(user)) {
            throw new Exception("You do not have permission");
          }

          JsonObject data = HandleData.dataToJson(req);

          String id = pathParts[1];

          String newRoleStr = data.get("role") != null ? data.get("role").getAsString() : null;

          UserService userService = new UserService();
          UserModel newUser = userService.updateRole(id, newRoleStr);

          JsonOne<UserModel> result = new JsonOne<UserModel>(newUser);

          String json = result.toString();
          printJson(json, 200, resp);
        } catch (Exception e) {
          printJsonError("fail", e.getMessage(), 403, resp);
        }
      }
      else {
        printJsonError("fail", "Not found", 404, resp);
      }

    }

  }
}
