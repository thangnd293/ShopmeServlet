package com.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleJson;
import com.api.helper.TokenJwt;

import org.json.JSONObject;

import io.jsonwebtoken.Claims;

// @WebFilter(urlPatterns = { "/api/v1/cart/*", "/api/v1/wishlist/*", "/api/v1/bill/*" })
public class CartnWishListFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            String jwt = req.getHeader("Authorization");
            boolean checkJwtIsNull = jwt == null;
            if (checkJwtIsNull) {
                throw new IOException("Requires login to authenticate user");
            }

            Claims claims = TokenJwt.checkJwt(jwt);

            JSONObject userJson = this.handleData(claims);
            String id = userJson.get("jti") != null ? userJson.get("jti").toString() : null;

            request.setAttribute("userId", id);

            chain.doFilter(request, response);
        } catch (Exception e) {
            resp.setContentType("application/json");
            HandleJson.printJsonError("fail", e.getMessage(), 403, resp);
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
