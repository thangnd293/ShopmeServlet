package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.helper.returnClass.JsonMany;
import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;
import com.api.service.filter.FilterService;

@WebServlet(urlPatterns = "/api/v1/filters/*")
public class Filter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            try {
                FilterService filterService = new FilterService();
                String categoryId = (String) req.getAttribute("categoryId");
                String json;
                if (categoryId == null) {
                    ArrayList<FilterModel> tags = filterService.getTag();
                    JsonMany<FilterModel> result = new JsonMany<FilterModel>(tags.size(), tags);
                    json = result.toString();
                } else {
                    ArrayList<Facet> facets = filterService.getFacets(categoryId);
                    JsonMany<Facet> result = new JsonMany<Facet>(facets.size(), facets);
                    json = result.toString();
                }
                
                printJson(json, 200, resp);
            } catch (Exception e) {
                printJsonError("fail", e.getMessage(), 404, resp);
            }
        } else {
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length != 2) {
                printJsonError("fail", "Not found", 404, resp);
            } else {
                FilterService filterService = new FilterService();
                String key = pathParts[1];
                ArrayList<FilterModel> filters = new ArrayList<FilterModel>();
                try {
                    if (key.equals("sizes")) {
                        filters = filterService.getSize();
                    } else if (key.equals("colors")) {
                        filters = filterService.getColor();
                    } else if (key.equals("brands")) {
                        filters = filterService.getBrand();
                    } else {
                        throw new Exception("Not found");
                    }

                    JsonMany<FilterModel> result = new JsonMany<FilterModel>(filters.size(), filters);
                    String json = result.toString();
                    printJson(json, 200, resp);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 404, resp);
                }
            }
        }
    }
}