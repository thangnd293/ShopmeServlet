package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import com.api.helper.HandleJson;
import com.api.model.user.UserMapping;
import com.api.model.user.UserModel;
import com.api.service.auth.AuthService;
import com.api.service.auth.IAuthService;
import com.api.service.user.IUserService;
import com.api.service.user.UserService;
import com.api.utils.Email;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/signup")
public class Signup extends HttpServlet {
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
     
  public Signup() {
      super();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    JsonObject data = HandleData.dataToJson(req);

    try {
      IAuthService authService = new AuthService();
      IUserService userService = new UserService();

      UserModel userSignup = UserMapping.map(data);
      UserModel user = authService.signup(userSignup);

      String subject = "Welcome to Shopme";

      String message = user.getVerifyCode();
      boolean checkIsEmailSend = Email.sendEmail(host, port, email, password, user.getEmail(), subject, message);
      
      if(!checkIsEmailSend) {
        userService.deleteUser(user.getId());
        throw new Exception("There were an error. Please try again!");
      }
      // String token = TokenJwt.generateJwt(user);
      // JsonAuth result = new JsonAuth(token, user);
      // String jsonString = new Gson().toJson(result).replace("\\\"", "");

      

      String jsonString = String.format("{ status: %s , message: %s }", "success" , "Please check your email to confirm your account");
      HandleJson.printJson(jsonString, 200, resp);
    } catch (Exception e) {
      HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
    }
  }
}
