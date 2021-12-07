package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.HandleData;
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.helper.returnClass.JsonOne;
import com.api.model.wishlist.WishlistModel;
import com.api.service.wishlist.WishListService;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/api/v1/wishlist/*")
public class WishList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            printJsonError("fail", "Not found", 404, resp);
        } else {
            try {
                WishListService wishListService = new WishListService();
                String userId = (String) req.getAttribute("userId");
                WishlistModel wishList = wishListService.getWishList(userId);
                JsonOne<WishlistModel> result = new JsonOne<WishlistModel>(wishList);

                String json = result.toString();
                printJson(json, 200, resp);
            } catch (Exception e) {
                printJsonError("fail", e.getMessage(), 404, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObject data = HandleData.dataToJson(req);
        String productId = data.get("product") != null ? data.get("product").getAsString() : null;
        String userId = (String) req.getAttribute("userId");

        WishListService wishListService = new WishListService();

        try {
            WishlistModel wishList = wishListService.toggleItem(userId, productId);

            JsonOne<WishlistModel> result = new JsonOne<WishlistModel>(wishList);

            String json = result.toString();
            printJson(json, 200, resp);
        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 400, resp);
        }

    }
}
