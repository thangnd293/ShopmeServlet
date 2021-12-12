package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.user.UserMapping;
import com.api.model.user.UserModel;
import com.api.service.auth.AuthService;
import com.api.service.user.UserService;
import com.api.utils.EmailUtil;
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
      AuthService authService = new AuthService();
      UserService userService = new UserService();

      UserModel userSignup = UserMapping.map(data);
      UserModel user = authService.signup(userSignup);

      this.sendEmail(user, userService);

      String json = String.format("{ status: %s , message: %s, email: %s }", "success" , "Please check your email to confirm your account", user.getEmail());
      
      printJson(json, 200, resp);
    } catch (Exception e) {
      printJsonError("fail", e.getMessage(), 404, resp);
    }
  }

  private void sendEmail(UserModel user, UserService userService) throws Exception {
    String subject = "Welcome to SummonShop (Valid for 5 minutes)";
    String path = this.getServletContext().getRealPath("/emailtemplate/emailVerify.html");

    String html = EmailUtil.getHtmlEmail(path);
    html = html.replace("<%NAME>", user.getFname());
    html = html.replace("<%CODE>", user.getVerifyCode());
    boolean checkIsEmailSend = EmailUtil.sendEmail(host, port, email, password, user.getEmail(), subject, html);

    if(!checkIsEmailSend) {
      userService.deleteUser(user.getId());
      throw new Exception("There were an error. Please try again!");
    }
  }
}
