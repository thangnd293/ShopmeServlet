package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import com.api.helper.HandleJson;
import com.api.helper.returnClass.JsonOne;
import com.api.model.product.ProductModel;
import com.api.model.variant.VariantMapping;
import com.api.model.variant.VariantModel;
import com.api.service.product.ProductService;
import com.api.service.variant.VariantService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/variants/*")
public class Variant extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        
        String pathInfo = req.getPathInfo();
        if(pathInfo == null) {
            HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");

            if(pathParts.length == 2) {
                try {
                    VariantService variantService = new VariantService();

                    ProductModel variant = variantService.getVariant(pathParts[1]);
                    
                    JsonOne<ProductModel> result = new JsonOne<ProductModel>(variant);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);
                } catch (Exception e) {
                    HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            JsonObject data = HandleData.dataToJson(req);
            VariantModel variant = VariantMapping.map(data);
            ProductService productService = new ProductService();
            String id = (String)req.getAttribute("productId");
            ProductModel product = productService.addVariant(id, variant);
            JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);
        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            String productId = (String)req.getAttribute("productId");
            String variantId = (String)req.getAttribute("variantId");

            ProductService productService = new ProductService();
            ProductModel product = productService.removeVariant(productId, variantId);
            JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);

        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            ProductService productService = new ProductService();
            JsonObject data = HandleData.dataToJson(req);
            VariantModel variant = VariantMapping.map(data);

            String productId = (String)req.getAttribute("productId");
            String variantId = (String)req.getAttribute("variantId");
            variant.setId(variantId);

            ProductModel product = productService.updateVariant(productId, variant);
            JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
            String jsonString = new Gson().toJson(result).replace("\\\"", "");
            HandleJson.printJson(jsonString, 200, resp);
        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
        }
    }
}
