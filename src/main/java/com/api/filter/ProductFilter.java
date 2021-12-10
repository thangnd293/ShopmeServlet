package com.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.user.UserModel;

public class ProductFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();

        if (method.equals("GET")) {
            chain.doFilter(req, response);
        } else {
            try {
                Check.checkLogged(req);
                UserModel user = (UserModel) req.getAttribute("user");

                if (!Check.isAdmin(user)) {
                    throw new Exception("You do not have permission");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                resp.setContentType("application/json");
                printJsonError("fail", e.getMessage(), 403, resp);
            }
        }
    }
}
