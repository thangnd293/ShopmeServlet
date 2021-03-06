package com.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cors implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cors.set(req, resp);
        // // CORS handshake (pre-flight request)
        if (req.getMethod().equals("OPTIONS")) {
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static void set(HttpServletRequest req, HttpServletResponse resp) {
        // Authorize the origin, all headers, and all methods
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods",
                "GET, OPTIONS, HEAD, PUT, POST, DELETE");
    }
}
