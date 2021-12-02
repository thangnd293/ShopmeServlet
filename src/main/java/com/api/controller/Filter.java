package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleJson;
import com.api.helper.returnClass.JsonMany;
import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;
import com.api.service.filter.FilterService;
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/api/v1/filters/*")
public class Filter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        if(pathInfo == null) {
            FilterService filterService = new FilterService();
            try {
            String categoryId = (String)req.getAttribute("categoryId");
            ArrayList<Facet> facets = filterService.getFacets(categoryId);
            JsonMany<Facet> result = new JsonMany<Facet>(facets.size(), facets);
            String jsonString = new Gson().toJson(result).replace("\\\"", "");
            HandleJson.printJson(jsonString, 200, resp);
            } catch (Exception e) {
                HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
            }
            // ArrayList<FilterModel> filters = filterService.getTopFilter();

            // JsonMany<FilterModel> result = new JsonMany<FilterModel>(filters.size(), filters);

            // String jsonString = new Gson().toJson(result).replace("\\\"", "");

            // HandleJson.printJson(jsonString, 200, resp);
        } else {
            String[] pathParts = pathInfo.split("/");

            if(pathParts.length != 2) {
                HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
            } else {
                FilterService filterService = new FilterService();
                String key = pathParts[1];
                ArrayList<FilterModel> filters = new ArrayList<FilterModel>();
                try {
                    if(Check.isNumeric(key)) {
                        filters = filterService.getSubFilters(key);
                    } else {
                        filters = filterService.getSubFiltersByType(pathParts[1]);
                    }
                } catch (Exception e) {
                    HandleJson.printJsonError("fail", "Invalid filter!!", 404, resp);
                }           
                JsonMany<FilterModel> result = new JsonMany<FilterModel>(filters.size(), filters);
                String jsonString = new Gson().toJson(result).replace("\\\"", "");
                 HandleJson.printJson(jsonString, 200, resp);          
            }
        }
    }
}