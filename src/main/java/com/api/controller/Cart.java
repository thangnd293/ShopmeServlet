package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleData;
import com.api.helper.HandleJson;
import com.api.helper.returnClass.JsonOne;
import com.api.model.cart.CartModel;
import com.api.service.cart.CartService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/cart/*")
public class Cart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            String userId = (String) req.getAttribute("userId");
            CartService cartService = new CartService();
            CartModel cart = cartService.getCart(userId);
            JsonOne<CartModel> result = new JsonOne<CartModel>(cart);
            String jsonString = new Gson().toJson(result).replace("\\\"", "");
            HandleJson.printJson(jsonString, 200, resp);
        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 404, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            HandleJson.printJsonError("fail", "Not found", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2 && pathParts[1].equals(("add-to-cart"))) {
                try {
                    JsonObject data = HandleData.dataToJson(req);
                    String userId = (String) req.getAttribute("userId");

                    String productVariation = data.get("productVariation") != null
                            ? data.get("productVariation").getAsString()
                            : null;
                    int quantity = data.get("quantity") != null ? data.get("quantity").getAsInt() : null;
                    CartService cartService = new CartService();
                    CartModel cart = cartService.addToCart(userId, productVariation, quantity);
                    JsonOne<CartModel> result = new JsonOne<CartModel>(cart);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);

                } catch (Exception e) {
                    HandleJson.printJsonError("fail", e.getMessage(), 400, resp);
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            JsonObject data = HandleData.dataToJson(req);
            String userId = (String) req.getAttribute("userId");

            String productVariation = data.get("productVariation") != null ? data.get("productVariation").getAsString()
                    : null;
            int quantity = data.get("quantity") != null ? data.get("quantity").getAsInt() : null;
            CartService cartService = new CartService();
            CartModel cart = cartService.updateCart(userId, productVariation, quantity);
            JsonOne<CartModel> result = new JsonOne<CartModel>(cart);
            String jsonString = new Gson().toJson(result).replace("\\\"", "");
            HandleJson.printJson(jsonString, 200, resp);

        } catch (Exception e) {
            HandleJson.printJsonError("fail", e.getMessage(), 400, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            HandleJson.printJsonError("fail", "Not found", 404, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2 && Check.isNumeric(pathParts[1])) {
                try {
                    String userId = (String) req.getAttribute("userId");

                    String productVariation = pathParts[1];

                    CartService cartService = new CartService();
                    CartModel cart = cartService.removeItem(userId, productVariation);
                    JsonOne<CartModel> result = new JsonOne<CartModel>(cart);
                    String jsonString = new Gson().toJson(result).replace("\\\"", "");
                    HandleJson.printJson(jsonString, 200, resp);

                } catch (Exception e) {
                    HandleJson.printJsonError("fail", e.getMessage(), 400, resp);
                }
                ;
            } else {
                HandleJson.printJsonError("fail", "Not found", 404, resp);
            }
        }

    }
}
