package com.api.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleData;
import com.api.helper.HandleJson;
import com.api.helper.returnClass.JsonMany;
import com.api.helper.returnClass.JsonOne;
import com.api.model.product.ProductMapping;
import com.api.model.product.ProductModel;
import com.api.service.product.IProductService;
import com.api.service.product.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/products/*")
public class Product extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        // api/v1/products
        // api/categories/:id/products
        if (pathInfo == null) {
            // HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);

            ProductService productService = new ProductService();
            ArrayList<String> fieldLimits = new ArrayList<String>();

            String categoryId = (String) req.getAttribute("categoryId");
            String[] filterParams = req.getParameterValues("p");
            String sortParam = req.getParameter("sort");

            fieldLimits.add("_id");
            fieldLimits.add("name");
            fieldLimits.add("brandName");
            fieldLimits.add("price");
            fieldLimits.add("discountPrice");
            fieldLimits.add("slug");
            fieldLimits.add("isFeatured");
            fieldLimits.add("createAt");
            fieldLimits.add("imageCovers");
            try {
                ArrayList<ProductModel> products = productService.getAllProduct(categoryId, filterParams, sortParam,
                        fieldLimits);

                JsonMany<ProductModel> result = new JsonMany<ProductModel>(products.size(), products);

                String jsonString = new Gson().toJson(result).replace("\\\"", "");
                HandleJson.printJson(jsonString, 200, resp);
            } catch (Exception e) {
                HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
            }
        }
        // Chia ra nhiều trường hợp
        else {
            String[] pathParts = pathInfo.split("/");
            // Trường hợp 1: /api/v1/product/:id
            if (pathParts.length == 2) {
                IProductService productService = new ProductService();
                System.out.println(req.getAttribute("user"));
                try {
                    ProductModel product = productService.getProduct(pathParts[1]);

                    JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
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
        String pathInfo = req.getPathInfo();
        if(pathInfo == null) {
            JsonObject data = HandleData.dataToJson(req);

            ProductService productService = new ProductService();
            
            try {
                ProductModel product = productService.addProduct(ProductMapping.map(data));
                JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
                String jsonString = new Gson().toJson(result).replace("\\\"", "");
                HandleJson.printJson(jsonString, 201, resp);
            } catch (Exception e) {
                HandleJson.printJsonError("fail", e.getMessage(), 400, resp);
                return;
            }
        } else {
            String[] pathParts = pathInfo.split("/");

            if(pathParts.length == 3 && Check.isNumeric(pathParts[1]) && pathParts[2].equals("variants")) {
                String productId = pathParts[1];
                req.setAttribute("productId", productId);

                this.getServletContext().getRequestDispatcher("/api/v1/variants").forward(req, resp);
            } else {
                HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
            }
        }
        

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if(pathInfo == null) {
            HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");

            if(pathParts.length == 2 && Check.isNumeric(pathParts[1])) {
                JsonObject data = HandleData.dataToJson(req);
                ProductService productService = new ProductService();
        
                try {
                    ProductModel product = ProductMapping.map(data);
                    product = productService.updateProduct(pathParts[1] , product);
        
                    JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);
                } catch (Exception e) {
                    HandleJson.printJsonError("fail", e.getMessage(), 400, resp);
                }
            } else if(pathParts.length == 4 && Check.isNumeric(pathParts[1]) && pathParts[2].equals("variants") && Check.isNumeric(pathParts[3])) {
                req.setAttribute("productId", pathParts[1]);
                req.setAttribute("variantId", pathParts[3]);

                this.getServletContext().getRequestDispatcher("/api/v1/variants").forward(req, resp);
            }
        }
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2 && Check.isNumeric(pathParts[1])) {
                try {
                    ProductService productService = new ProductService();
                    productService.deleteProduct(pathParts[1]);
                    resp.setStatus(204);
                } catch (Exception e) {
                    HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
                }

            } else if(pathParts.length == 4 && Check.isNumeric(pathParts[1]) && pathParts[2].equals("variants")) {
                String productId = pathParts[1];
                String variantId = pathParts[3];
                req.setAttribute("productId", productId);
                req.setAttribute("variantId", variantId);

                this.getServletContext().getRequestDispatcher("/api/v1/variants").forward(req, resp);
            } else {
                HandleJson.printJsonError("fail", "Invalid path!!", 404, resp);
            }
        }
    }
}
