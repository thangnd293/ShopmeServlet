package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.service.dashboard.DashBoardService;

@WebServlet(urlPatterns = "/api/v1/dashboard")
public class DashBoard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DashBoardService dashBoardService = new DashBoardService();
        dashBoardService.getDashBoard();
    }
}
