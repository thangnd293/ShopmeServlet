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

@WebFilter(urlPatterns = { "/api/v1/user/*", "/api/v1/cart/*", "/api/v1/wishlist/*", "/api/v1/bill/*",
    "/api/v1/dashboard" })
public class AuthFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    Cors.set(req, resp);
    if (req.getMethod().equals("OPTIONS")) {
      resp.setStatus(HttpServletResponse.SC_ACCEPTED);
      return;
    }

    try {
      Check.checkLogged(req);
      chain.doFilter(request, response);
    } catch (Exception e) {
      resp.setContentType("application/json");
      printJsonError("fail", e.getMessage(), 403, resp);
    }
  }
}