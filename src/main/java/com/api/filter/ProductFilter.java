package com.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import static com.api.helper.HandleJson.printJsonError;
import com.api.model.user.UserModel;

@WebFilter(urlPatterns = { "/api/v1/products/*" })
public class ProductFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();

        Cors.set(req, resp);
        if (method.equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        if (method.equals("GET")) {
            chain.doFilter(request, response);

        } else {

            try {
                Check.checkLogged(req);
                UserModel user = (UserModel) req.getAttribute("user");

                if (!Check.isAdmin(user)) {
                    throw new Exception("You do not have permission");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                // CROSFilter.setCorsHeader(resp);
                resp.setContentType("application/json");
                printJsonError("fail", e.getMessage(), 403, resp);
            }
        }
    }
}
