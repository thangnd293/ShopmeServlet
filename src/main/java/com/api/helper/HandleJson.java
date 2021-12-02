package com.api.helper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.api.helper.returnClass.JsonError;
import com.google.gson.Gson;

import org.json.JSONObject;

public class HandleJson {
    public static <Model> void printJson(String jsonString, int code, HttpServletResponse resp) throws IOException {

        JSONObject json = new JSONObject(jsonString);

        resp.setStatus(code);
        PrintWriter pw = resp.getWriter();

        pw.print(json);

        pw.close();
    }

    public static <Model> void printJsonError(String status, String message, int statusCode, HttpServletResponse resp)
            throws IOException {
        JsonError result = new JsonError(status, message);

        String jsonString = new Gson().toJson(result).replace("\\\"", "");

        JSONObject jsonCategories = new JSONObject(jsonString);

        resp.setStatus(statusCode);
        PrintWriter pw = resp.getWriter();

        pw.print(jsonCategories);

        pw.close();
    }

}
