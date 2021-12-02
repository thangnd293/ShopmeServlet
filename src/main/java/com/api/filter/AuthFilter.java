package com.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.api.helper.TokenJwt;

import org.json.JSONObject;

import io.jsonwebtoken.Claims;

@WebFilter(urlPatterns = { "/api/v1/user/*" })
public class AuthFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    String jwt = httpServletRequest.getHeader("Authorization");
    System.out.println("Authorization: " + jwt);

    boolean checkJwtIsNull = jwt == null;
    if (checkJwtIsNull) {
    throw new IOException("Requires login to authenticate user");
    }

    try {
    Claims claims = TokenJwt.checkJwt(jwt);

    JSONObject userJson = this.handleData(claims);
    request.setAttribute("user", userJson);

    chain.doFilter(request, response);
    } catch (Exception e) {
    throw new IOException("Incorrect or expired token");
    }
  }

  private JSONObject handleData(Claims claims) {
    JSONObject userJson = new JSONObject();
    claims.forEach((key, value) -> {
      String val = value.toString().replace("\"", "");
      userJson.put(key, val);
    });

    return userJson;
  }
}