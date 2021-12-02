package com.api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;

public class DemoMethod extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter pw = resp.getWriter();

    if (req.getQueryString() == null) {
      pw.close();
      return;
    }

    pw.println(HandleData.queryStringToJson(req));
    pw.close();
    return;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    PrintWriter pw = resp.getWriter();

    System.out.println(HandleData.dataToJson(req));
    pw.close();
  }
}
