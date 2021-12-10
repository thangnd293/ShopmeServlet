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
      UserModel user = (UserModel) req.getAttribute("user");
      JsonObject data = HandleData.dataToJson(req);

      // api/v1/user/me
      // Cap nhat avt
      if (pathParts.length == 2 && pathParts[1].equals("me")) {
        this.userUpdateUsersAvt(data, user, resp);
      }
      // api/v1/user/update-password
      // Cap nhat mat khau
      else if (pathParts.length == 2 && pathParts[1].equals("update-password")) {
        this.userUpdateUsersPassword(data, user, resp);
      }
      // api/v1/user/:id
      // admin cap nhat quyen cho user
      else if (pathParts.length == 2) {
        this.adminUpdateRole(user, pathParts[1], data, resp);
      } else {
        printJsonError("fail", "Not found", 404, resp);
      }
    }
  }

  private void userUpdateUsersAvt(JsonObject data, UserModel user, HttpServletResponse resp) throws IOException {
    String photo = data.get("photo") != null ? data.get("photo").getAsString() : null;

    if(photo == null) {
      printJsonError("fail", "Invalid photo", 404, resp);
    } else {
      UserService userService = new UserService();
      try {
        user.setPhoto(photo);
        user = userService.updatePhoto(user.getId(), user);
        JsonOne<UserModel> result = new JsonOne<UserModel>(user);
  
        String json = result.toString();
        printJson(json, 200, resp);
      } catch (Exception e) {
        printJsonError("fail", e.getMessage(), 404, resp);
      }
    }
  }

  private void userUpdateUsersPassword(JsonObject data, UserModel user, HttpServletResponse resp) throws IOException {
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

  private void adminUpdateRole(UserModel admin, String userId, JsonObject data, HttpServletResponse resp)
      throws IOException {
    try {
      if (!Check.isAdmin(admin)) {
        throw new Exception("You do not have permission");
      }
      String newRoleStr = data.get("role") != null ? data.get("role").getAsString() : null;

      UserService userService = new UserService();
      UserModel newUser = userService.updateRole(userId, newRoleStr);

      JsonOne<UserModel> result = new JsonOne<UserModel>(newUser);

      String json = result.toString();
      printJson(json, 200, resp);
    } catch (Exception e) {
      printJsonError("fail", e.getMessage(), 403, resp);
    }
  }
}
