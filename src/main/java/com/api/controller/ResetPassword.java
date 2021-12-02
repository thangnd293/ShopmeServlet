package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import com.api.helper.HandleJson;
import com.api.helper.TokenJwt;
import com.api.helper.returnClass.JsonAuth;
import com.api.model.user.UserModel;
import com.api.service.auth.AuthService;
import com.api.service.auth.IAuthService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/reset-password")
public class ResetPassword extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObject data = HandleData.dataToJson(req);
        String email = data.get("email") != null ? data.get("email").getAsString() : null;
        String resetCode = data.get("resetCode") != null ? data.get("resetCode").getAsString() : null;
        String password = data.get("password") != null ? data.get("password").getAsString() : null;
        String passwordConfirm = data.get("passwordConfirm") != null ? data.get("passwordConfirm").getAsString() : null;
        try {
            IAuthService authService = new AuthService();
            UserModel user = authService.resetPassword(email, resetCode, password, passwordConfirm);

            String token = TokenJwt.generateJwt(user);
            JsonAuth result = new JsonAuth(token, user);
            String jsonString = new Gson().toJson(result).replace("\\\"", "");
            HandleJson.printJson(jsonString, 200, resp);
        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
        }
    }
}
