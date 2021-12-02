package com.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleJson;
import com.api.helper.TokenJwt;
import com.api.model.user.UserModel;
import com.api.utils.FilterUlti;

import org.json.JSONObject;

import io.jsonwebtoken.Claims;

@WebFilter(urlPatterns = { "/api/v1/products/*" })
public class ProductFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();
        ArrayList<String> restrictedMethod = new ArrayList<String>(Arrays.asList("POST", "PUT", "DELETE"));

        if (restrictedMethod.contains(method)) {
            try {
                String jwt = req.getHeader("Authorization");
                boolean checkJwtIsNull = jwt == null;
                if (checkJwtIsNull) {
                    throw new IOException("Requires login to authenticate user");
                }

                Claims claims = TokenJwt.checkJwt(jwt);

                JSONObject userJson = this.handleData(claims);

                String id = userJson.get("jti") != null ? userJson.get("jti").toString() : null;


                UserModel user = FilterUlti.checkAdmin(id);

                request.setAttribute("user", user);

                chain.doFilter(request, response);
            } catch (Exception e) {
                resp.setContentType("application/json");
                HandleJson.printJsonError("fail", e.getMessage(), 403, resp);
            }
        } else {
            chain.doFilter(request, response);
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
