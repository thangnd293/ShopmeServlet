package com.api.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.helper.Check;
import com.api.helper.HandleData;
import static com.api.helper.HandleJson.printJson;
import static com.api.helper.HandleJson.printJsonError;
import com.api.helper.returnClass.JsonOne;
import com.api.model.user.UserModel;
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
                UserModel user = (UserModel) req.getAttribute("user");

                if (!Check.isUser(user)) {
                    throw new Exception("You do not have permission");
                }

                WishListService wishListService = new WishListService();
                WishlistModel wishList = wishListService.getWishList(user.getId());
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

        WishListService wishListService = new WishListService();

        try {

            UserModel user = (UserModel) req.getAttribute("user");

            if (!Check.isUser(user)) {
                throw new Exception("You do not have permission");
            }
            WishlistModel wishList = wishListService.toggleItem(user.getId(), productId);

            JsonOne<WishlistModel> result = new JsonOne<WishlistModel>(wishList);

            String json = result.toString();
            printJson(json, 200, resp);
        } catch (Exception e) {
            printJsonError("fail", e.getMessage(), 400, resp);
        }

    }
}
