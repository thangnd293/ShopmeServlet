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
import com.api.model.category.CategoryModel;
import com.api.service.category.CategoryService;

@WebServlet(urlPatterns = "/api/v1/categories/*")
public class Category extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        // String[] tedt = req.getParameterValues("test");
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            // Trường hợp 1: /api/v1/categories/:id
            // if (pathParts.length == 2) {
            // ICategoryService categoryService = new CategoryService();
            // try {
            // CategoryModel category = categoryService.getCategory(pathParts[1]);
            // getSubCategoriesByParent(categoryService, category, resp);
            // } catch (Exception e) {
            // HandleJson.printJsonError("fail",e.getMessage(), 404, resp);
            // }
            // }

            // Trường hợp 2: /api/v1/categories/:id/products
            if (pathParts.length == 3 && pathParts[2].equals("products")) {
                String categoryId = pathParts[1];
                req.setAttribute("categoryId", categoryId);
                this.getServletContext().getRequestDispatcher("/api/v1/products").forward(req, resp);
            }

            // Trường hợp 3: /api/v1/categories/:id/products/facets
            else if (pathParts.length == 4 && pathParts[2].equals("products") && pathParts[3].equals("facets")) {
                String categoryId = pathParts[1];
                req.setAttribute("categoryId", categoryId);
                this.getServletContext().getRequestDispatcher("/api/v1/filters").forward(req, resp);
            }

            else {
                printJsonError("fail", "Not found", 404, resp);
            }
        } else {
            try {
                CategoryService categoryService = new CategoryService();
                ArrayList<CategoryModel> categories = categoryService.getAllCategories();

                JsonMany<CategoryModel> result = new JsonMany<CategoryModel>(categories.size(), categories);

                String json = result.toString();

                printJson(json, 200, resp);

            } catch (Exception e) {
                printJsonError("fail", e.getMessage(), 404, resp);
            }
        }
    };

    // private void getSubCategoriesByParent(ICategoryService categoryService,
    // CategoryModel category,
    // HttpServletResponse resp) throws IOException {
    // ArrayList<CategoryModel> subCategories =
    // categoryService.getSubCategoriesByParent(category.getPath());
    // JsonMany<CategoryModel> result = new
    // JsonMany<CategoryModel>(subCategories.size(), subCategories);
    // String jsonString = new Gson().toJson(result).replace("\\\"", "");
    // HandleJson.printJson(jsonString, 200, resp);
    // }
}
