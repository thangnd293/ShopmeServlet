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
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.helper.returnClass.JsonMany;
import com.api.helper.returnClass.JsonOne;
import com.api.model.product.ProductMapping;
import com.api.model.product.ProductModel;
import com.api.service.product.ProductService;
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
            ProductService productService = new ProductService();

            String categoryId = (String) req.getAttribute("categoryId");

            String[] filterParams = req.getParameterValues("p");
            String sortParam = req.getParameter("sort");

            try {
                ArrayList<ProductModel> products = productService.getAllProduct(categoryId, filterParams, sortParam);

                JsonMany<ProductModel> result = new JsonMany<ProductModel>(products.size(), products);

                String json = result.toString();
                printJson(json, 200, resp);
            } catch (Exception e) {
                printJsonError("fail", e.getMessage(), 404, resp);
            }
        }
        // Chia ra nhiều trường hợp
        else {
            String[] pathParts = pathInfo.split("/");
            
            // /api/v1/product/features
            if (pathParts.length == 2 && pathParts[1].equals("features")) {
                ProductService productService = new ProductService();
                try {
                    ArrayList<ProductModel> products = productService.getProductFeatures();

                    JsonMany<ProductModel> result = new JsonMany<ProductModel>(products.size(), products);
                    String json = result.toString();
                    printJson(json, 200, resp);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 404, resp);
                }
            }
            // /api/v1/product/:id
            else if (pathParts.length == 2) {
                ProductService productService = new ProductService();
                try {
                    ProductModel product = productService.getProduct(pathParts[1]);

                    JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);

                    String json = result.toString();
                    printJson(json, 200, resp);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 404, resp);
                }
            } 
            else {
                printJsonError("fail", "Not found", 404, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JsonObject data = HandleData.dataToJson(req);

        ProductService productService = new ProductService();

        try {
            ProductModel product = productService.addProduct(ProductMapping.map(data));

            JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
            String json = result.toString();

            printJson(json, 201, resp);
        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 400, resp);
        }

        // String pathInfo = req.getPathInfo();
        // if(pathInfo == null) {
        // JsonObject data = HandleData.dataToJson(req);

        // ProductService productService = new ProductService();

        // try {
        // ProductModel product = productService.addProduct(ProductMapping.map(data));
        // JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
        // String jsonString = result.toString();
        // printJson(jsonString, 201, resp);
        // } catch (Exception e) {
        // printJsonError("fail", e.getMessage(), 400, resp);
        // }
        // } else {
        // String[] pathParts = pathInfo.split("/");

        // if(pathParts.length == 3 && Check.isNumeric(pathParts[1]) &&
        // pathParts[2].equals("variants")) {
        // String productId = pathParts[1];
        // req.setAttribute("productId", productId);

        // this.getServletContext().getRequestDispatcher("/api/v1/variants").forward(req,
        // resp);
        // } else {
        // printJsonError("fail", "Invalid path!!", 404, resp);
        // }
        // }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            printJsonError("fail", "Not found", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && Check.isNumeric(pathParts[1])) {
                JsonObject data = HandleData.dataToJson(req);
                ProductService productService = new ProductService();

                try {
                    ProductModel product = ProductMapping.map(data);
                    product = productService.updateProduct(pathParts[1], product);

                    JsonOne<ProductModel> result = new JsonOne<ProductModel>(product);
                    String jsonString = result.toString();
                    printJson(jsonString, 200, resp);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 400, resp);
                }
            } else {
                printJsonError("fail", "Not found", 404, resp);
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null) {
            printJsonError("fail", "Not found", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2 && Check.isNumeric(pathParts[1])) {
                try {
                    ProductService productService = new ProductService();
                    productService.deleteProduct(pathParts[1]);
                    resp.setStatus(204);
                } catch (Exception e) {
                    printJsonError("fail", e.getMessage(), 404, resp);
                }

            } else {
                printJsonError("fail", "Not found", 404, resp);
            }

            // else if (pathParts.length == 4 && Check.isNumeric(pathParts[1]) &&
            // pathParts[2].equals("variants")) {
            // String productId = pathParts[1];
            // String variantId = pathParts[3];
            // req.setAttribute("productId", productId);
            // req.setAttribute("variantId", variantId);

            // this.getServletContext().getRequestDispatcher("/api/v1/variants").forward(req,
            // resp);
            // } else {
            // printJsonError("fail", "Invalid path!!", 404, resp);
            // }
        }
    }
}
