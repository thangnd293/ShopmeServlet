package com.api.helper;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HandleData {
  public static <Model> Model dataToClass(HttpServletRequest req, Class<Model> model)
      throws ServletException, IOException {
    return new Gson().fromJson(req.getReader(), model);
  }

  public static JsonObject dataToJson(HttpServletRequest req) throws ServletException, IOException {
    String body = req.getReader().lines().collect(Collectors.joining());

    JsonObject json = new Gson().fromJson(body, JsonObject.class);

    // String parts = body.substring(1, body.length() - 1).replace("\"", "");
    // for (String part : parts.split(",")) {
    //   String[] keyVal = part.split(":");
    //   json.addProperty(keyVal[0].trim(), keyVal[1].trim());
    // }

    return json;
  }

  public static JsonObject queryStringToJson(HttpServletRequest req) throws ServletException, IOException {
    String myStringDecoded = URLDecoder.decode(req.getQueryString(), "UTF-8");

    JsonObject json = new JsonObject();

    String[] parts = myStringDecoded.split("&");
    for (String part : parts) {
      String[] keyVal = part.split("=");
      json.addProperty(keyVal[0], keyVal[1]);
    }

    return json;
  }

}
