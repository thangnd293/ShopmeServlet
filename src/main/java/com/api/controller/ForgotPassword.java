package com.api.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.service.auth.AuthService;
import com.api.utils.EmailUtil;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/forgot-password")
public class ForgotPassword extends HttpServlet {

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

    public ForgotPassword() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JsonObject data = HandleData.dataToJson(req);
        String userEmail = data.get("email") != null ? data.get("email").getAsString() : null;
        try {
            AuthService authService = new AuthService();

            String passwordResetCode = authService.forgotPassword(userEmail);

            boolean checkIsEmailSend = this.sendEmail(userEmail, passwordResetCode);

            if (!checkIsEmailSend) {
                throw new Exception("There were an error. Please try again!");
            }

            String json = String.format("{ status: %s , message: %s, email: %s }", "success",
                    "Please check your email to get your reset password code", userEmail);
            printJson(json, 200, resp);

        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    private boolean sendEmail(String userEmail, String passwordResetCode) throws AddressException, MessagingException {
        String subject = "Reset your password (Valid for 5 minutes)";
            String path = this.getServletContext().getRealPath("/emailtemplate/resetpassword.html");

            String html = EmailUtil.getHtmlEmail(path);
            html = html.replace("<%CODE>", passwordResetCode);

            return EmailUtil.sendEmail(host, port, email, password, userEmail, subject, html);
    }
}
