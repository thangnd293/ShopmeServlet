package com.api.helper;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HandleData {
  public static JsonObject dataToJson(HttpServletRequest req) throws ServletException, IOException {
    String body = req.getReader().lines().collect(Collectors.joining());

    JsonObject json = new Gson().fromJson(body, JsonObject.class);

    return json;
  }
}
